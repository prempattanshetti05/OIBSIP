
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.library.model.User" %>

        <% User user=(User) session.getAttribute("user"); if (user==null || session.getAttribute("userId")==null ||
            !"ADMIN".equalsIgnoreCase(user.getRole())) { response.sendRedirect( request.getContextPath()
            + "/auth/login.jsp" ); return; } %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Admin Dashboard - Digital Library</title>

                <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
            </head>

            <body>

                <div class="dashboard-container">

                    <header class="dashboard-header">

                        <div>
                            <h1>Digital Library - Admin</h1>
                            <p>Welcome, <%= user.getName() %>!</p>
                        </div>

                        <a href="<%= request.getContextPath() %>/logout" class="logout-btn">
                            Logout
                        </a>

                    </header>

                    <main class="dashboard-content">

                        <h2>Admin Dashboard</h2>

                        <p>
                            Manage books, members, issues, fines and user queries.
                        </p>

                        <div class="dashboard-grid">

                            <!-- Manage Books -->
                            <div class="dashboard-card">

                                <h3>📚 Manage Books</h3>

                                <p>
                                    Add, edit and delete books from the catalogue.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/books">
                                    Manage Books
                                </a>

                            </div>

                            <!-- Issued Books -->
                            <div class="dashboard-card">

                                <h3>📖 Issued Books</h3>

                                <p>
                                    View issued books, member details and due dates.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/issued-books">
                                    Issued Books
                                </a>

                            </div>

                            <!-- Reservation Management -->
                            <div class="dashboard-card">
                                <h3>📅 Reservations</h3>
                                <p>View pending reservations and issue available copies to waiting students.</p>
                                <a href="<%= request.getContextPath() %>/admin/reservations">Manage Reservations</a>
                            </div>

                            <!-- Members -->
                            <div class="dashboard-card">

                                <h3>👥 Members</h3>

                                <p>
                                    View registered library members.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/members">
                                    View Members
                                </a>

                            </div>

                            <!-- Fine Management -->
                            <div class="dashboard-card">

                                <h3>💰 Fine Management</h3>

                                <p>
                                    View overdue fines and manage payment status.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/fines">
                                    Manage Fines
                                </a>

                            </div>

                            <!-- User Queries -->
                            <div class="dashboard-card">

                                <h3>📩 Queries</h3>

                                <p>
                                    View user messages and resolve their queries.
                                </p>

                                <a href="<%= request.getContextPath() %>/admin/queries">
                                    View Queries
                                </a>

                            </div>

                        </div>

                    </main>

                </div>

            </body>

            </html>
            