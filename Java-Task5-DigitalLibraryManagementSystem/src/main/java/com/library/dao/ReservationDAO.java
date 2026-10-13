
package com.library.dao;

import com.library.model.Reservation;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Create a reservation for an unavailable book
    public boolean reserveBook(int userId, int bookId) {

        String checkBookSql = "SELECT available_quantity FROM books WHERE id = ?";

        String checkReservationSql = "SELECT id FROM reservations "
                + "WHERE user_id = ? AND book_id = ? "
                + "AND status = 'PENDING'";

        String insertSql = "INSERT INTO reservations (user_id, book_id, status) "
                + "VALUES (?, ?, 'PENDING')";

        try (Connection con = DBConnection.getConnection()) {

            // Confirm that the book exists and is unavailable
            try (PreparedStatement ps = con.prepareStatement(checkBookSql)) {

                ps.setInt(1, bookId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() || rs.getInt("available_quantity") > 0) {
                        return false;
                    }
                }
            }

            // Prevent duplicate pending reservations
            try (PreparedStatement ps = con.prepareStatement(checkReservationSql)) {

                ps.setInt(1, userId);
                ps.setInt(2, bookId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return false;
                    }
                }
            }

            // Save reservation
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {

                ps.setInt(1, userId);
                ps.setInt(2, bookId);

                return ps.executeUpdate() == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve reservations belonging to one user
    public List<Reservation> getReservationsByUserId(int userId) {

        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT r.id, r.user_id, r.book_id, "
                + "u.name AS user_name, "
                + "b.title AS book_title, "
                + "r.status, r.reservation_date "
                + "FROM reservations r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN books b ON r.book_id = b.id "
                + "WHERE r.user_id = ? "
                + "ORDER BY r.reservation_date DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapReservation(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Retrieve all reservations for the administrator
    public List<Reservation> getAllReservations() {

        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT r.id, r.user_id, r.book_id, "
                + "u.name AS user_name, "
                + "b.title AS book_title, "
                + "r.status, r.reservation_date "
                + "FROM reservations r "
                + "JOIN users u ON r.user_id = u.id "
                + "JOIN books b ON r.book_id = b.id "
                + "ORDER BY r.reservation_date DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Cancel a reservation belonging to the specified user
    public boolean cancelReservation(int reservationId, int userId) {

        String sql = "UPDATE reservations SET status = 'CANCELLED' "
                + "WHERE id = ? AND user_id = ? AND status = 'PENDING'";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Admin fulfills the oldest pending reservation for a book by issuing one available copy.
    public boolean fulfillReservation(int reservationId) {
        try (Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                int bookId, userId;
                try (PreparedStatement ps=c.prepareStatement(
                        "SELECT book_id, user_id FROM reservations WHERE id=? AND status='PENDING'")) {
                    ps.setInt(1,reservationId);
                    try(ResultSet rs=ps.executeQuery()) {
                        if(!rs.next()) { c.rollback(); return false; }
                        bookId=rs.getInt(1); userId=rs.getInt(2);
                    }
                }
                // Lock the book before checking availability and reservation priority.
                try(PreparedStatement ps=c.prepareStatement("SELECT available_quantity FROM books WHERE id=? FOR UPDATE")) {
                    ps.setInt(1,bookId);
                    try(ResultSet rs=ps.executeQuery()) {
                        if(!rs.next() || rs.getInt(1)<=0) { c.rollback(); return false; }
                    }
                }
                try(PreparedStatement ps=c.prepareStatement(
                        "SELECT id FROM reservations WHERE book_id=? AND status='PENDING' ORDER BY reservation_date,id LIMIT 1 FOR UPDATE")) {
                    ps.setInt(1,bookId);
                    try(ResultSet rs=ps.executeQuery()) {
                        if(!rs.next() || rs.getInt(1)!=reservationId) { c.rollback(); return false; }
                    }
                }
                try(PreparedStatement ps=c.prepareStatement(
                        "UPDATE books SET available_quantity=available_quantity-1 WHERE id=? AND available_quantity>0")) {
                    ps.setInt(1,bookId);
                    if(ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                try(PreparedStatement ps=c.prepareStatement(
                        "INSERT INTO issues(user_id,book_id,issue_date,due_date) VALUES(?,?,?,?)")) {
                    java.time.LocalDate today=java.time.LocalDate.now();
                    ps.setInt(1,userId); ps.setInt(2,bookId);
                    ps.setDate(3,java.sql.Date.valueOf(today));
                    ps.setDate(4,java.sql.Date.valueOf(today.plusDays(14)));
                    if(ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                try(PreparedStatement ps=c.prepareStatement(
                        "UPDATE reservations SET status='FULFILLED' WHERE id=? AND status='PENDING'")) {
                    ps.setInt(1,reservationId);
                    if(ps.executeUpdate()!=1) { c.rollback(); return false; }
                }
                c.commit(); return true;
            } catch(SQLException e) { c.rollback(); e.printStackTrace(); return false; }
            finally { c.setAutoCommit(true); }
        } catch(SQLException e) { e.printStackTrace(); return false; }
    }

    // Convert a database row into a Reservation object
    private Reservation mapReservation(ResultSet rs)
            throws SQLException {

        Reservation reservation = new Reservation();

        reservation.setId(rs.getInt("id"));
        reservation.setUserId(rs.getInt("user_id"));
        reservation.setBookId(rs.getInt("book_id"));
        reservation.setUserName(rs.getString("user_name"));
        reservation.setBookTitle(rs.getString("book_title"));
        reservation.setStatus(rs.getString("status"));
        reservation.setReservationDate(
                rs.getTimestamp("reservation_date"));

        return reservation;
    }
}
