package com.library.controller;

import com.library.dao.QueryDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/admin/queries")
public class AdminQueriesServlet extends HttpServlet {

    private final QueryDAO queryDAO = new QueryDAO();

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

        request.setAttribute("queries", queryDAO.getAllQueries());

        request.getRequestDispatcher("/admin/queries.jsp")
                .forward(request, response);
    }
}