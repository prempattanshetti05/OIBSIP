package com.library.controller;

import com.library.dao.UserDAO;
import com.library.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("========== REGISTER REQUEST ==========");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password received: " +
                (password != null ? "YES" : "NO"));

        // Validate empty fields
        if (name == null || email == null || password == null ||
                name.trim().isEmpty() ||
                email.trim().isEmpty() ||
                password.trim().isEmpty()) {

            System.out.println("Registration failed: Empty field");
            response.sendRedirect("auth/register.jsp?error=empty");
            return;
        }

        name = name.trim();
        email = email.trim();

        // Check whether email already exists
        if (userDAO.emailExists(email)) {
            System.out.println("Registration failed: Email already exists");
            response.sendRedirect("auth/register.jsp?error=exists");
            return;
        }

        // Create user
        User user = new User(
                name,
                email,
                password,
                "USER");

        // Save user
        boolean registered = userDAO.registerUser(user);

        System.out.println("Registration result: " + registered);

        if (registered) {

            System.out.println("Registration successful!");
            response.sendRedirect("auth/login.jsp?registered=true");

        } else {

            System.out.println("Registration failed: Database insert failed");
            response.sendRedirect("auth/register.jsp?error=failed");
        }

        System.out.println("======================================");
    }
}