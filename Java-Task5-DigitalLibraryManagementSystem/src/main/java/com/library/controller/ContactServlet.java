package com.library.controller;

import com.library.dao.QueryDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/user/contact")
public class ContactServlet extends HttpServlet {

    private final QueryDAO queryDAO = new QueryDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/auth/login.jsp");
            return;
        }

        request.getRequestDispatcher("/user/contact.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/auth/login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        if (subject == null || message == null) {
            response.sendRedirect(
                    request.getContextPath() + "/user/contact?error=invalid");
            return;
        }

        subject = subject.trim();
        message = message.trim();

        if (subject.isEmpty() || message.isEmpty()
                || subject.length() > 200
                || message.length() > 5000) {
            response.sendRedirect(
                    request.getContextPath() + "/user/contact?error=invalid");
            return;
        }

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString());

        boolean submitted = queryDAO.submitQuery(userId, subject, message);

        response.sendRedirect(
                request.getContextPath() + "/user/contact?result=" +
                        (submitted ? "sent" : "error"));
    }
}