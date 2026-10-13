package com.library.controller;

import com.library.dao.IssueDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/issue-book")
public class IssueBookServlet extends HttpServlet {

    private final IssueDAO issueDAO = new IssueDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Prevent administrators from using the user issue action
        String role = (String) session.getAttribute("role");

        if (role != null && "ADMIN".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Administrators cannot issue books through this page.");
            return;
        }

        int userId;
        int bookId;

        try {
            userId = Integer.parseInt(
                    session.getAttribute("userId").toString());

            bookId = Integer.parseInt(request.getParameter("bookId"));

            if (bookId <= 0) {
                throw new NumberFormatException("Invalid book ID");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/books?error=invalid");
            return;
        }

        boolean issued = issueDAO.issueBook(userId, bookId);

        if (issued) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/books?success=issued");
        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/books?error=issue");
        }
    }
}