
package com.library.controller;

import com.library.dao.ReservationDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/reserve-book")
public class ReserveBookServlet extends HttpServlet {

    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doPost(HttpServletRequest request,
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

        // Save the reservation
        boolean reserved = reservationDAO.reserveBook(userId, bookId);

        if (reserved) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/books?success=reserved");
        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/books?error=reserve");
        }
    }
}
