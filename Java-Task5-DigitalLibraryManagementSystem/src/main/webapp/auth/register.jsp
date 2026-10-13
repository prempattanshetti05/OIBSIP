<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Create Account | Digital Library</title>

        <link rel="stylesheet" href="../css/style.css?v=2">
    </head>

    <body>

        <div class="auth-page">

            <div class="auth-card">

                <div class="brand">
                    <div class="brand-icon">📚</div>
                    <h1>Digital Library</h1>
                    <p>Create your library account</p>
                </div>

                <% if ("exists".equals(request.getParameter("error"))) { %>
                    <div class="alert error">
                        An account with this email already exists.
                    </div>
                    <% } %>

                        <% if ("empty".equals(request.getParameter("error"))) { %>
                            <div class="alert error">
                                Please fill in all fields.
                            </div>
                            <% } %>

                                <% if ("failed".equals(request.getParameter("error"))) { %>
                                    <div class="alert error">
                                        Registration failed. Please try again.
                                    </div>
                                    <% } %>

                                        <form action="../register" method="post">

                                            <div class="form-group">
                                                <label for="name">Full Name</label>
                                                <input type="text" id="name" name="name"
                                                    placeholder="Enter your full name" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="email">Email Address</label>
                                                <input type="email" id="email" name="email"
                                                    placeholder="Enter your email" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="password">Password</label>
                                                <input type="password" id="password" name="password"
                                                    placeholder="Create a password" required>
                                            </div>

                                            <button type="submit" class="btn-primary">
                                                Create Account
                                            </button>

                                        </form>

                                        <div class="auth-footer">
                                            Already have an account?
                                            <a href="login.jsp">Login</a>
                                        </div>

            </div>

        </div>

    </body>

    </html>