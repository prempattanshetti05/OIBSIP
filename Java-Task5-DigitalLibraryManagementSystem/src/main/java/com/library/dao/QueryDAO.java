package com.library.dao;

import com.library.model.Query;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDAO {

    public boolean submitQuery(int userId, String subject, String message) {

        String sql = "INSERT INTO queries (user_id, subject, message) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, subject);
            ps.setString(3, message);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Query> getAllQueries() {

        List<Query> queries = new ArrayList<>();

        String sql = "SELECT q.id, q.user_id, u.name AS user_name, " +
                "q.subject, q.message, q.status, q.created_at " +
                "FROM queries q JOIN users u ON q.user_id = u.id " +
                "ORDER BY q.created_at DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Query query = new Query();

                query.setId(rs.getInt("id"));
                query.setUserId(rs.getInt("user_id"));
                query.setUserName(rs.getString("user_name"));
                query.setSubject(rs.getString("subject"));
                query.setMessage(rs.getString("message"));
                query.setStatus(rs.getString("status"));
                query.setCreatedAt(rs.getTimestamp("created_at"));

                queries.add(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }

    public boolean resolveQuery(int queryId) {

        String sql = "UPDATE queries SET status = 'RESOLVED' " +
                "WHERE id = ? AND status = 'OPEN'";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, queryId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}