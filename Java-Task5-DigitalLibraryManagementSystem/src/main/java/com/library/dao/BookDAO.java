package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    // ==========================================
// SEARCH BOOKS
// ==========================================
public List<Book> searchBooks(String search, String category) {

    List<Book> books = new ArrayList<>();

    String sql = """
            SELECT id,
                   title,
                   author,
                   isbn,
                   category,
                   quantity,
                   available_quantity
            FROM books
            WHERE (title LIKE ? OR author LIKE ?)
              AND (? = '' OR category = ?)
            ORDER BY title
            """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        String searchText = "";

        if (search != null) {
            searchText = search.trim();
        }

        String searchPattern = "%" + searchText + "%";

        String categoryText = "";

        if (category != null) {
            categoryText = category.trim();
        }


        statement.setString(1, searchPattern);
        statement.setString(2, searchPattern);
        statement.setString(3, categoryText);
        statement.setString(4, categoryText);


        try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("isbn"),
                        resultSet.getString("category"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("available_quantity")
                );

                books.add(book);
            }
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return books;
}

    // ==========================================
    // ADD BOOK
    // ==========================================
    public boolean addBook(Book book) {

        String sql = """
                INSERT INTO books
                (title, author, isbn, category, quantity, available_quantity)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setString(4, book.getCategory());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // GET ALL BOOKS
    // ==========================================
    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = """
                SELECT id,
                       title,
                       author,
                       isbn,
                       category,
                       quantity,
                       available_quantity
                FROM books
                ORDER BY id DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("isbn"),
                        resultSet.getString("category"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("available_quantity")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }


    // ==========================================
    // GET BOOK BY ID
    // ==========================================
    public Book getBookById(int id) {

        String sql = """
                SELECT id,
                       title,
                       author,
                       isbn,
                       category,
                       quantity,
                       available_quantity
                FROM books
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Book(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("isbn"),
                            resultSet.getString("category"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("available_quantity")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // UPDATE BOOK
    // ==========================================
    public boolean updateBook(Book book) {

        String sql = """
                UPDATE books
                SET title = ?,
                    author = ?,
                    isbn = ?,
                    category = ?,
                    quantity = ?,
                    available_quantity = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.setString(4, book.getCategory());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getAvailableQuantity());
            statement.setInt(7, book.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // DELETE BOOK
    // ==========================================
    public boolean deleteBook(int id) {

        String sql = """
                DELETE FROM books
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        
    }
}