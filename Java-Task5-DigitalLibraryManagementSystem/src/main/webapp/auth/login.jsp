<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Login | Digital Library</title>

        <link rel="stylesheet" href="../css/style.css?v=2">
    </head>

    <body>

        <div class="auth-page">

            <div class="auth-card">

                <div class="brand">
                    <div class="brand-icon">📚</div>
                    <h1>Digital Library</h1>
                    <p>Welcome back</p>
                </div>

                <% if ("registered".equals(request.getParameter("registered"))) { %>
                    <div class="alert success">
                        Registration successful. You can now login.
                    </div>
                    <% } %>

                        <% if ("invalid".equals(request.getParameter("error"))) { %>
                            <div class="alert error">
                                Invalid email or password.
                            </div>
                            <% } %>

                                <% if ("empty".equals(request.getParameter("error"))) { %>
                                    <div class="alert error">
                                        Please enter email and password.
                                    </div>
                                    <% } %>

                                        <form action="../login" method="post">

                                            <div class="form-group">
                                                <label for="email">Email Address</label>
                                                <input type="email" id="email" name="email"
                                                    placeholder="Enter your email" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="password">Password</label>
                                                <input type="password" id="password" name="password"
                                                    placeholder="Enter your password" required>
                                            </div>

                                            <button type="submit" class="btn-primary">
                                                Login
                                            </button>

                                        </form>

                                        <div class="auth-footer">
                                            Don't have an account?
                                            <a href="register.jsp">Create Account</a>
                                        </div>

            </div>

        </div>

    </body>

    </html>