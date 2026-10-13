package com.library.controller;

import com.library.dao.IssueDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/admin/issued-books")
public class AdminIssuedBooksServlet extends HttpServlet {

    private final IssueDAO issueDAO = new IssueDAO();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !"ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("issues", issueDAO.getAllIssues());

        request.getRequestDispatcher("/admin/issued-books.jsp")
                .forward(request, response);
    }
}