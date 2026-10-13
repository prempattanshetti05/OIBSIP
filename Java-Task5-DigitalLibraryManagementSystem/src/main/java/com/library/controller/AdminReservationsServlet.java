package com.library.controller;
import com.library.dao.ReservationDAO;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
@WebServlet("/admin/reservations")
public class AdminReservationsServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        HttpSession s=req.getSession(false);
        if(s==null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("userRole")))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN); return;
        }
        List<String[]> rows=new ArrayList<>();
        String sql="SELECT r.id,u.name,b.title,b.available_quantity,r.status,r.reservation_date " +
                   "FROM reservations r JOIN users u ON r.user_id=u.id " +
                   "JOIN books b ON r.book_id=b.id ORDER BY r.reservation_date,r.id";
        try(Connection c=DBConnection.getConnection();PreparedStatement ps=c.prepareStatement(sql);ResultSet rs=ps.executeQuery()) {
            while(rs.next()) rows.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)});
        } catch(SQLException e) { throw new ServletException(e); }
        req.setAttribute("reservationRows",rows);
        req.getRequestDispatcher("/admin/reservations.jsp").forward(req,resp);
    }
}
