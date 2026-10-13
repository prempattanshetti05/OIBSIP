package com.library.controller;
import com.library.dao.IssueDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
@WebServlet("/admin/approve-return")
public class AdminApproveReturnServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s=req.getSession(false);
        if (s==null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("userRole")))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN); return;
        }
        boolean ok=false;
        try { ok=new IssueDAO().approveReturn(Integer.parseInt(req.getParameter("issueId"))); }
        catch (NumberFormatException ignored) { }
        resp.sendRedirect(req.getContextPath()+"/admin/issued-books?"+(ok?"success=approved":"error=return"));
    }
}
