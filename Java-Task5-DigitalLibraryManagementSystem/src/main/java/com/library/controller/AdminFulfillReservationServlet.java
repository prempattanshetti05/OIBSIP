package com.library.controller;
import com.library.dao.ReservationDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
@WebServlet("/admin/fulfill-reservation")
public class AdminFulfillReservationServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        HttpSession s=req.getSession(false);
        if(s==null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("userRole")))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN); return;
        }
        boolean ok=false;
        try { ok=new ReservationDAO().fulfillReservation(Integer.parseInt(req.getParameter("reservationId"))); }
        catch(NumberFormatException ignored) { }
        resp.sendRedirect(req.getContextPath()+"/admin/reservations?"+(ok?"success=fulfilled":"error=unavailable-or-not-first"));
    }
}
