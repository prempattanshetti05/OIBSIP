
package com.library.dao;

import com.library.model.Fine;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FineDAO {

    // Fine rate: ₹5 per overdue day
    private static final double FINE_PER_DAY = 5.0;

    // Retrieve all fines belonging to a user
    public List<Fine> getFinesByUserId(int userId) {

        List<Fine> fines = new ArrayList<>();

        String sql = "SELECT f.id, f.issue_id, f.user_id, "
                + "u.name AS user_name, b.title AS book_title, "
                + "f.amount, f.paid, f.created_at "
                + "FROM fines f "
                + "JOIN users u ON f.user_id = u.id "
                + "JOIN issues i ON f.issue_id = i.id "
                + "JOIN books b ON i.book_id = b.id "
                + "WHERE f.user_id = ? "
                + "ORDER BY f.created_at DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    fines.add(mapFine(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fines;
    }

    // Retrieve all fines for the administrator
    public List<Fine> getAllFines() {

        List<Fine> fines = new ArrayList<>();

        String sql = "SELECT f.id, f.issue_id, f.user_id, "
                + "u.name AS user_name, b.title AS book_title, "
                + "f.amount, f.paid, f.created_at "
                + "FROM fines f "
                + "JOIN users u ON f.user_id = u.id "
                + "JOIN issues i ON f.issue_id = i.id "
                + "JOIN books b ON i.book_id = b.id "
                + "ORDER BY f.paid ASC, f.created_at DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                fines.add(mapFine(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fines;
    }

    // Mark a fine as paid
    public boolean markFinePaid(int fineId) {

        String sql = "UPDATE fines SET paid = TRUE "
                + "WHERE id = ? AND paid = FALSE";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, fineId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create a fine for an overdue returned book.
    // Call this when returning a book, before committing the return.
    public boolean createFineIfOverdue(
            Connection con,
            int issueId,
            int userId,
            java.sql.Date dueDate,
            java.sql.Date returnDate) throws SQLException {

        if (dueDate == null || returnDate == null
                || !returnDate.after(dueDate)) {
            return true;
        }

        long overdueMillis = returnDate.getTime() - dueDate.getTime();

        long overdueDays = overdueMillis / (24L * 60 * 60 * 1000);

        if (overdueDays <= 0) {
            return true;
        }

        double amount = overdueDays * FINE_PER_DAY;

        // Avoid duplicate fine records for the same issue
        String checkSql = "SELECT id FROM fines WHERE issue_id = ?";

        try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            checkPs.setInt(1, issueId);

            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }

        String insertSql = "INSERT INTO fines (issue_id, user_id, amount, paid) "
                + "VALUES (?, ?, ?, FALSE)";

        try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {

            insertPs.setInt(1, issueId);
            insertPs.setInt(2, userId);
            insertPs.setDouble(3, amount);

            return insertPs.executeUpdate() == 1;
        }
    }

    // Convert a database row into a Fine object
    private Fine mapFine(ResultSet rs) throws SQLException {

        Fine fine = new Fine();

        fine.setId(rs.getInt("id"));
        fine.setIssueId(rs.getInt("issue_id"));
        fine.setUserId(rs.getInt("user_id"));
        fine.setUserName(rs.getString("user_name"));
        fine.setBookTitle(rs.getString("book_title"));
        fine.setAmount(rs.getDouble("amount"));
        fine.setPaid(rs.getBoolean("paid"));
        fine.setCreatedAt(rs.getTimestamp("created_at"));

        return fine;
    }
}
