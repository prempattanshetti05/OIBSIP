
package com.library.controller;

import com.library.dao.ReservationDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/cancel-reservation")
public class CancelReservationServlet extends HttpServlet {

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
        int reservationId;

        try {
            userId = Integer.parseInt(
                    session.getAttribute("userId").toString());

            reservationId = Integer.parseInt(
                    request.getParameter("reservationId"));

            if (reservationId <= 0) {
                throw new NumberFormatException(
                        "Invalid reservation ID");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/reservations?error=invalid");
            return;
        }

        // Cancel only a pending reservation belonging to this user
        boolean cancelled = reservationDAO.cancelReservation(
                reservationId, userId);

        if (cancelled) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/reservations?success=cancelled");
        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/user/reservations?error=cancel");
        }
    }
}