
package com.library.controller;

import com.library.dao.ReservationDAO;
import com.library.model.Reservation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/reservations")
public class UserReservationsServlet extends HttpServlet {

    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verify login
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

        // Retrieve reservations for the logged-in user
        List<Reservation> reservations =
                reservationDAO.getReservationsByUserId(userId);

        request.setAttribute("reservations", reservations);

        // Forward to the Reservations page
        request.getRequestDispatcher("/user/reservations.jsp")
               .forward(request, response);
    }
}
