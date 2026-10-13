package com.library.controller;

import com.library.dao.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/books")
public class UserBooksServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check whether user is logged in
        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect("../auth/login.jsp");
            return;
        }


        // Get search and category values
        String search = request.getParameter("search");
        String category = request.getParameter("category");


        // Get books from database
        request.setAttribute(
                "books",
                bookDAO.searchBooks(search, category)
        );


        // Send books to JSP
        request.getRequestDispatcher(
                "/user/books.jsp"
        ).forward(request, response);
    }
}