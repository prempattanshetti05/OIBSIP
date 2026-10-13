package com.library.dao;

import com.library.model.User;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = """
                INSERT INTO users (name, email, password, role)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            int rows = statement.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            return rows > 0;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "REGISTRATION DATABASE ERROR: "
                            + e.getMessage()
                            + " | SQL State: "
                            + e.getSQLState()
                            + " | Error Code: "
                            + e.getErrorCode(),
                    e);
        }
    }

    public User loginUser(String email, String password) {

        String sql = """
                SELECT id, name, email, password, role
                FROM users
                WHERE email = ? AND password = ?
                """;

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"));
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public boolean emailExists(String email) {

        String sql = """
                SELECT id
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}