package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("========== LOGIN REQUEST ==========");
        System.out.println("Email: " + email);
        System.out.println("Password received: " +
                (password != null ? "YES" : "NO"));

        // Validate fields
        if (email == null || password == null ||
                email.trim().isEmpty() ||
                password.trim().isEmpty()) {

            System.out.println("Login failed: Empty field");
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=empty");
            return;
        }

        email = email.trim();

        // Check user credentials
        User user = userDAO.loginUser(email, password);

        if (user != null) {

            System.out.println("Login successful");
            System.out.println("User ID: " + user.getId());
            System.out.println("User Role: " + user.getRole());

            // Create session
            HttpSession session = request.getSession();

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userRole", user.getRole());

            // Redirect based on role
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {

                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");

            } else {

                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
            }

        } else {

            System.out.println("Login failed: Invalid email or password");

            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=invalid");
        }

        System.out.println("===================================");
    }
}