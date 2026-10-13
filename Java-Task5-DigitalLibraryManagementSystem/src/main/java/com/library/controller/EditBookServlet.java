package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/edit-book")
public class EditBookServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    // Open Edit Book page
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {
            response.sendRedirect("books");
            return;
        }

        try {

            int id = Integer.parseInt(idText);

            Book book = bookDAO.getBookById(id);

            if (book == null) {
                response.sendRedirect("books?error=notfound");
                return;
            }

            request.setAttribute("book", book);

            request.getRequestDispatcher("/admin/edit-book.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect("books?error=invalid");
        }
    }


    // Save edited book
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String isbn = request.getParameter("isbn");
            String category = request.getParameter("category");

            int quantity = Integer.parseInt(
                    request.getParameter("quantity")
            );

            int availableQuantity = Integer.parseInt(
                    request.getParameter("availableQuantity")
            );


            // Validate text fields
            if (title == null || author == null ||
                    isbn == null || category == null ||
                    title.trim().isEmpty() ||
                    author.trim().isEmpty() ||
                    isbn.trim().isEmpty() ||
                    category.trim().isEmpty()) {

                response.sendRedirect(
                        "edit-book?id=" + id + "&error=empty"
                );

                return;
            }


            // Validate quantity
            if (quantity < 0 ||
                    availableQuantity < 0 ||
                    availableQuantity > quantity) {

                response.sendRedirect(
                        "edit-book?id=" + id + "&error=quantity"
                );

                return;
            }


            Book book = new Book(
                    id,
                    title.trim(),
                    author.trim(),
                    isbn.trim(),
                    category.trim(),
                    quantity,
                    availableQuantity
            );


            boolean updated = bookDAO.updateBook(book);


            if (updated) {

                response.sendRedirect(
                        "books?success=updated"
                );

            } else {

                response.sendRedirect(
                        "edit-book?id=" + id + "&error=failed"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "books?error=invalid"
            );
        }
    }
}