package com.library.controller;

import com.library.util.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/members")
public class AdminMembersServlet extends HttpServlet {

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

        List<String[]> members = new ArrayList<>();

        String sql = "SELECT id, name, email, created_at FROM users " +
                "WHERE role = 'USER' ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                members.add(new String[] {
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        String.valueOf(rs.getTimestamp("created_at"))
                });
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load members.", e);
        }

        request.setAttribute("members", members);

        request.getRequestDispatcher("/admin/members.jsp")
                .forward(request, response);
    }
}