package com.library.dao;

import com.library.model.Issue;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    // Issue a book and reduce its available quantity
    public boolean issueBook(int userId, int bookId) {

        String checkSql = "SELECT available_quantity FROM books WHERE id = ? FOR UPDATE";

        String updateBookSql = "UPDATE books SET available_quantity = available_quantity - 1 "
                + "WHERE id = ? AND available_quantity > 0";

        String insertIssueSql = "INSERT INTO issues (user_id, book_id, issue_date, due_date) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                // Lock the book row while checking availability
                try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {

                    checkPs.setInt(1, bookId);

                    try (ResultSet rs = checkPs.executeQuery()) {
                        if (!rs.next() || rs.getInt("available_quantity") <= 0) {
                            con.rollback();
                            return false;
                        }
                    }
                }

                // Decrease available quantity
                try (PreparedStatement updatePs = con.prepareStatement(updateBookSql)) {

                    updatePs.setInt(1, bookId);

                    if (updatePs.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // Set issue date and due date
                LocalDate issueDate = LocalDate.now();
                LocalDate dueDate = issueDate.plusDays(14);

                try (PreparedStatement insertPs = con.prepareStatement(insertIssueSql)) {

                    insertPs.setInt(1, userId);
                    insertPs.setInt(2, bookId);
                    insertPs.setDate(3, Date.valueOf(issueDate));
                    insertPs.setDate(4, Date.valueOf(dueDate));

                    insertPs.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all currently issued books for a particular user
    public List<Issue> getIssuesByUserId(int userId) {

        List<Issue> issues = new ArrayList<>();

        String sql = "SELECT i.id, i.user_id, i.book_id, "
                + "u.name AS user_name, u.email AS user_email, "
                + "b.title AS book_title, "
                + "i.issue_date, i.due_date, i.return_date, COALESCE((SELECT rr.status FROM return_requests rr WHERE rr.issue_id=i.id), 'NONE') AS return_status "
                + "FROM issues i "
                + "JOIN users u ON i.user_id = u.id "
                + "JOIN books b ON i.book_id = b.id "
                + "WHERE i.user_id = ? AND i.return_date IS NULL "
                + "ORDER BY i.due_date";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    issues.add(mapIssue(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issues;
    }

    // Retrieve all issue records for the admin
    public List<Issue> getAllIssues() {

        List<Issue> issues = new ArrayList<>();

        String sql = "SELECT i.id, i.user_id, i.book_id, "
                + "u.name AS user_name, u.email AS user_email, "
                + "b.title AS book_title, "
                + "i.issue_date, i.due_date, i.return_date, COALESCE((SELECT rr.status FROM return_requests rr WHERE rr.issue_id=i.id), 'NONE') AS return_status "
                + "FROM issues i "
                + "JOIN users u ON i.user_id = u.id "
                + "JOIN books b ON i.book_id = b.id "
                + "ORDER BY i.issue_date DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                issues.add(mapIssue(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issues;
    }

    // Student requests a return; stock and issue records remain unchanged until admin approval.
    public boolean requestReturn(int issueId, int userId) {
        String sql = "INSERT INTO return_requests (issue_id, user_id, status) " +
                "SELECT i.id, i.user_id, 'PENDING' FROM issues i " +
                "WHERE i.id=? AND i.user_id=? AND i.return_date IS NULL " +
                "AND NOT EXISTS (SELECT 1 FROM return_requests r WHERE r.issue_id=i.id)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1, issueId); ps.setInt(2, userId);
            return ps.executeUpdate()==1;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // Only an administrator calls this method after physically receiving the book.
    public boolean approveReturn(int issueId) {
        try (Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                int bookId, userId;
                Date dueDate;
                try (PreparedStatement ps=c.prepareStatement(
                        "SELECT i.book_id, i.user_id, i.due_date FROM issues i " +
                        "JOIN return_requests r ON r.issue_id=i.id " +
                        "WHERE i.id=? AND i.return_date IS NULL AND r.status='PENDING' FOR UPDATE")) {
                    ps.setInt(1, issueId);
                    try (ResultSet rs=ps.executeQuery()) {
                        if (!rs.next()) { c.rollback(); return false; }
                        bookId=rs.getInt(1); userId=rs.getInt(2); dueDate=rs.getDate(3);
                    }
                }
                Date today=Date.valueOf(LocalDate.now());
                try (PreparedStatement ps=c.prepareStatement("UPDATE issues SET return_date=? WHERE id=? AND return_date IS NULL")) {
                    ps.setDate(1,today); ps.setInt(2,issueId);
                    if (ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                if (!new FineDAO().createFineIfOverdue(c,issueId,userId,dueDate,today)) {
                    c.rollback(); return false;
                }
                try (PreparedStatement ps=c.prepareStatement(
                        "UPDATE books SET available_quantity=LEAST(quantity,available_quantity+1) WHERE id=?")) {
                    ps.setInt(1,bookId);
                    if (ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                try (PreparedStatement ps=c.prepareStatement(
                        "UPDATE return_requests SET status='APPROVED', reviewed_at=CURRENT_TIMESTAMP WHERE issue_id=? AND status='PENDING'")) {
                    ps.setInt(1,issueId);
                    if (ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                c.commit(); return true;
            } catch(SQLException e) { c.rollback(); e.printStackTrace(); return false; }
            finally { c.setAutoCommit(true); }
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    // Convert a database row into an Issue object
    private Issue mapIssue(ResultSet rs) throws SQLException {

        Issue issue = new Issue();

        issue.setReturnStatus(rs.getString("return_status"));
        issue.setId(rs.getInt("id"));
        issue.setUserId(rs.getInt("user_id"));
        issue.setBookId(rs.getInt("book_id"));
        issue.setUserName(rs.getString("user_name"));
        issue.setUserEmail(rs.getString("user_email"));
        issue.setBookTitle(rs.getString("book_title"));
        issue.setIssueDate(rs.getDate("issue_date"));
        issue.setDueDate(rs.getDate("due_date"));
        issue.setReturnDate(rs.getDate("return_date"));

        return issue;
    }
}
