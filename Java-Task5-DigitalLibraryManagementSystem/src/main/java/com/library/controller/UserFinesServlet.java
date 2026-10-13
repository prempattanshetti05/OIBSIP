package com.library.controller;

import com.library.dao.FineDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/user/fines")
public class UserFinesServlet extends HttpServlet {

    private final FineDAO fineDAO = new FineDAO();

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

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString());

        request.setAttribute(
                "fines", fineDAO.getFinesByUserId(userId));

        request.getRequestDispatcher("/user/fines.jsp")
                .forward(request, response);
    }
}
