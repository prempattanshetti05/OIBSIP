package com.library.controller;

import com.library.dao.IssueDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/return-book")
public class ReturnBookServlet extends HttpServlet {

    private final IssueDAO issueDAO = new IssueDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check login
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId;
        int issueId;

        try {
            userId = Integer.parseInt(
                    session.getAttribute("userId").toString());

            issueId = Integer.parseInt(
                    request.getParameter("issueId"));

            if (issueId <= 0) {
                throw new NumberFormatException("Invalid issue ID");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/my-books?error=invalid");
            return;
        }

        // Return only an issue belonging to the logged-in user
        boolean returned = issueDAO.requestReturn(issueId, userId);

        if (returned) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/my-books?success=requested");
        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/my-books?error=return");
        }
    }
}