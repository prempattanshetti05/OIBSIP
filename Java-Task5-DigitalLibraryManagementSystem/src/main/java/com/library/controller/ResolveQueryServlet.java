package com.library.controller;

import com.library.dao.QueryDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/resolve-query")
public class ResolveQueryServlet extends HttpServlet {

    private final QueryDAO queryDAO = new QueryDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !"ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            int queryId = Integer.parseInt(
                    request.getParameter("queryId"));

            if (queryId <= 0) {
                throw new NumberFormatException();
            }

            boolean resolved = queryDAO.resolveQuery(queryId);

            response.sendRedirect(
                    request.getContextPath() + "/admin/queries?result=" +
                            (resolved ? "resolved" : "error"));

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/admin/queries?result=error");
        }
    }
}
