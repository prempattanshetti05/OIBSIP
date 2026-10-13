
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.library.model.User" %>

        <% User user=(User) session.getAttribute("user"); if (user==null || session.getAttribute("userId")==null) {
            response.sendRedirect( request.getContextPath() + "/auth/login.jsp" ); return; } %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>User Dashboard - Digital Library</title>

                <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
            </head>

            <body>

                <div class="dashboard-container">

                    <header class="dashboard-header">
                        <div>
                            <h1>Digital Library</h1>
                            <p>Welcome, <%= user.getName() %>!</p>
                        </div>

                        <a href="<%= request.getContextPath() %>/logout" class="logout-btn">
                            Logout
                        </a>
                    </header>

                    <main class="dashboard-content">

                        <h2>User Dashboard</h2>
                        <p>Manage your books and library activities from here.</p>

                        <div class="dashboard-grid">

                            <!-- Browse Books -->
                            <div class="dashboard-card">
                                <h3>📚 Browse Books</h3>
                                <p>Search and explore available books.</p>
                                <a href="<%= request.getContextPath() %>/user/books">
                                    Browse Books
                                </a>
                            </div>

                            <!-- My Books -->
                            <div class="dashboard-card">
                                <h3>📖 My Books</h3>
                                <p>View books you have issued and their due dates.</p>
                                <a href="<%= request.getContextPath() %>/user/my-books">
                                    My Books
                                </a>
                            </div>

                            <!-- Reservations -->
                            <div class="dashboard-card">
                                <h3>📅 Reservations</h3>
                                <p>View your advance book reservations.</p>
                                <a href="<%= request.getContextPath() %>/user/reservations">
                                    Reservations
                                </a>
                            </div>

                            <!-- Fines -->
                            <div class="dashboard-card">
                                <h3>💰 Fines</h3>
                                <p>Check your pending and paid fines.</p>
                                <a href="<%= request.getContextPath() %>/user/fines">
                                    View Fines
                                </a>
                            </div>

                            <!-- Contact Library -->
                            <div class="dashboard-card">
                                <h3>📩 Contact Library</h3>
                                <p>Send a query to the library administrator.</p>
                                <a href="<%= request.getContextPath() %>/user/contact">
                                    Contact Admin
                                </a>
                            </div>

                        </div>

                    </main>

                </div>

            </body>

            </html>
            