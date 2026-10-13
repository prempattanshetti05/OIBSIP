package com.library.controller;

import com.library.dao.FineDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/admin/fines")
public class AdminFinesServlet extends HttpServlet {

    private final FineDAO fineDAO = new FineDAO();

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

        request.setAttribute("fines", fineDAO.getAllFines());

        request.getRequestDispatcher("/admin/fines.jsp")
                .forward(request, response);
    }
}
