package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/add-book")
public class AddBookServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");
        String quantityText = request.getParameter("quantity");

        if (title == null || author == null || isbn == null ||
                category == null || quantityText == null ||
                title.trim().isEmpty() ||
                author.trim().isEmpty() ||
                isbn.trim().isEmpty() ||
                category.trim().isEmpty() ||
                quantityText.trim().isEmpty()) {

            response.sendRedirect("add-book.jsp?error=empty");
            return;
        }

        try {

            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {
                response.sendRedirect("add-book.jsp?error=quantity");
                return;
            }

            Book book = new Book(
                    title.trim(),
                    author.trim(),
                    isbn.trim(),
                    category.trim(),
                    quantity,
                    quantity);

            boolean added = bookDAO.addBook(book);

            if (added) {
                response.sendRedirect("books.jsp?success=added");
            } else {
                response.sendRedirect("add-book.jsp?error=failed");
            }

        } catch (NumberFormatException e) {

            response.sendRedirect("add-book.jsp?error=quantity");
        }
    }
}
