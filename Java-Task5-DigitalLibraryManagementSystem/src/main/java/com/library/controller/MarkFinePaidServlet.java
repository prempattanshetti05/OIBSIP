package com.library.controller;

import com.library.dao.FineDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/admin/mark-fine-paid")
public class MarkFinePaidServlet extends HttpServlet {

    private final FineDAO fineDAO = new FineDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !"ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            int fineId = Integer.parseInt(
                    request.getParameter("fineId"));

            if (fineId <= 0) {
                throw new NumberFormatException();
            }

            boolean updated = fineDAO.markFinePaid(fineId);

            response.sendRedirect(
                    request.getContextPath() + "/admin/fines?success=" +
                            (updated ? "paid" : "error"));

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/admin/fines?success=error");
        }
    }
}
