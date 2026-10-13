package com.library.controller;

import com.library.dao.BookDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-book")
public class DeleteBookServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {
            response.sendRedirect("books?error=delete");
            return;
        }

        try {

            int id = Integer.parseInt(idText);

            boolean deleted = bookDAO.deleteBook(id);

            if (deleted) {

                response.sendRedirect(
                        "books?success=deleted"
                );

            } else {

                response.sendRedirect(
                        "books?error=delete"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "books?error=delete"
            );
        }
    }
}