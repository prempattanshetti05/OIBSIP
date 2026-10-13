package com.library.controller;

import com.library.dao.IssueDAO;
import com.library.model.Issue;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/my-books")
public class MyBooksServlet extends HttpServlet {

    private final IssueDAO issueDAO = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check whether the user is logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId;

        try {
            userId = Integer.parseInt(
                    session.getAttribute("userId").toString());
        } catch (NumberFormatException e) {
            session.invalidate();
            response.sendRedirect(
                    request.getContextPath() + "/auth/login.jsp");
            return;
        }

        // Retrieve the user's currently borrowed books
        List<Issue> issues = issueDAO.getIssuesByUserId(userId);

        request.setAttribute("issues", issues);

        // Display the My Books page
        request.getRequestDispatcher("/user/my-books.jsp")
               .forward(request, response);
    }
}