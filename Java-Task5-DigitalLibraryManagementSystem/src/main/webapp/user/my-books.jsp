
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Issue" %>
        <%@ page import="java.time.LocalDate" %>

            <% List<Issue> issues = (List<Issue>) request.getAttribute("issues");

                    String success = request.getParameter("success");
                    String error = request.getParameter("error");
                    %>

                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>My Books - Digital Library</title>

                        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">

                        <style>
                            .page-container {
                                width: 90%;
                                max-width: 1200px;
                                margin: 40px auto;
                            }

                            .table-container {
                                overflow-x: auto;
                            }

                            table {
                                width: 100%;
                                border-collapse: collapse;
                                background: white;
                                margin-top: 20px;
                            }

                            th,
                            td {
                                padding: 14px;
                                border-bottom: 1px solid #ddd;
                                text-align: left;
                            }

                            th {
                                background: #f3f4f6;
                            }

                            .message {
                                padding: 12px;
                                margin: 15px 0;
                                border-radius: 6px;
                            }

                            .success {
                                background: #dcfce7;
                                color: #166534;
                            }

                            .error {
                                background: #fee2e2;
                                color: #991b1b;
                            }

                            .overdue {
                                color: #dc2626;
                                font-weight: bold;
                            }

                            .active {
                                color: #15803d;
                                font-weight: bold;
                            }

                            .return-button {
                                background: #2563eb;
                                color: white;
                                padding: 8px 12px;
                                border: none;
                                border-radius: 5px;
                                cursor: pointer;
                            }

                            .return-button:hover {
                                background: #1d4ed8;
                            }

                            .empty-message {
                                text-align: center;
                                padding: 25px;
                                color: #666;
                            }
                        </style>
                    </head>

                    <body>

                        <div class="page-container">

                            <div class="topbar">
                                <h1>My Borrowed Books</h1>

                                <a class="btn danger" href="<%= request.getContextPath() %>/logout">
                                    Logout
                                </a>
                            </div>

                            <p>View your borrowed books and their return deadlines.</p>

                            <% if ("requested".equals(success)) { %>
                                <div class="message success">
                                    Return request submitted. Waiting for admin approval.
                                </div>
                                <% } %>

                                    <% if ("invalid".equals(error)) { %>
                                        <div class="message error">
                                            Invalid issue selection. Please try again.
                                        </div>
                                        <% } else if ("return".equals(error)) { %>
                                            <div class="message error">
                                                Unable to return this book. Please try again.
                                            </div>
                                            <% } %>

                                                <div class="table-container">
                                                    <table>
                                                        <thead>
                                                            <tr>
                                                                <th>Book Title</th>
                                                                <th>Issue Date</th>
                                                                <th>Due Date</th>
                                                                <th>Status</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>

                                                            <% if (issues !=null && !issues.isEmpty()) { %>

                                                                <% for (Issue issue : issues) { boolean
                                                                    overdue=issue.getDueDate() !=null &&
                                                                    issue.getDueDate().toLocalDate()
                                                                    .isBefore(LocalDate.now()); %>

                                                                    <tr>
                                                                        <td>
                                                                            <%= issue.getBookTitle() %>
                                                                        </td>

                                                                        <td>
                                                                            <%= issue.getIssueDate() %>
                                                                        </td>

                                                                        <td>
                                                                            <%= issue.getDueDate() %>
                                                                        </td>

                                                                        <td>
                                                                            <% if ("PENDING".equals(issue.getReturnStatus())) { %>
                                                                                <span class="overdue">Return Pending (Admin Approval)</span>
                                                                            <% } else if (overdue) { %>
                                                                                <span class="overdue">Overdue</span>
                                                                                <% } else { %>
                                                                                    <span class="active">Borrowed</span>
                                                                                    <% } %>
                                                                        </td>

                                                                        <td>
                                                                            <% if (!"PENDING".equals(issue.getReturnStatus())) { %>
                                                                            <form method="post"
                                                                                action="<%= request.getContextPath() %>/user/return-book"
                                                                                onsubmit="return confirm('Are you sure you want to return this book?');">

                                                                                <input type="hidden" name="issueId"
                                                                                    value="<%= issue.getId() %>">

                                                                                <button type="submit"
                                                                                    class="return-button">
                                                                                    Return Book
                                                                                </button>
                                                                            </form>
                                                                            <% } else { %>Awaiting admin approval<% } %>
                                                                        </td>
                                                                    </tr>

                                                                    <% } %>

                                                                        <% } else { %>

                                                                            <tr>
                                                                                <td colspan="5" class="empty-message">
                                                                                    You have no currently borrowed
                                                                                    books.
                                                                                </td>
                                                                            </tr>

                                                                            <% } %>

                                                        </tbody>
                                                    </table>
                                                </div>

                                                <p>
                                                    <a href="<%= request.getContextPath() %>/user/dashboard.jsp">
                                                         Back to Dashboard
                                                    </a>
                                                </p>

                        </div>

                    </body>

                    </html>
                    