
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Issue" %>

        <% List<Issue> issues = (List<Issue>) request.getAttribute("issues");
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Issued Books - Digital Library</title>
                    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
                    <style>
                        .container {
                            width: 95%;
                            max-width: 1250px;
                            margin: 35px auto;
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                            background: white;
                        }

                        th,
                        td {
                            padding: 12px;
                            border-bottom: 1px solid #ddd;
                            text-align: left;
                        }

                        th {
                            background: #f3f4f6;
                        }

                        .overdue {
                            color: #dc2626;
                            font-weight: bold;
                        }

                        .returned {
                            color: #15803d;
                            font-weight: bold;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <h1>Issued Books and Due Dates</h1>

                        <div style="overflow-x:auto">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Member</th>
                                        <th>Email</th>
                                        <th>Book</th>
                                        <th>Issue Date</th>
                                        <th>Due Date</th>
                                        <th>Return Date</th>
                                        <th>Status</th><th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% if (issues !=null && !issues.isEmpty()) { for (Issue issue : issues) { boolean
                                        overdue=issue.getReturnDate()==null && issue.getDueDate() !=null &&
                                        issue.getDueDate().toLocalDate() .isBefore(java.time.LocalDate.now()); %>
                                        <tr>
                                            <td>
                                                <%= issue.getUserName() %>
                                            </td>
                                            <td>
                                                <%= issue.getUserEmail() %>
                                            </td>
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
                                                <%= issue.getReturnDate()==null ? "Not returned" : issue.getReturnDate()
                                                    %>
                                            </td>
                                            <td>
                                                <% if ("PENDING".equals(issue.getReturnStatus())) { %>Return Pending<% } else if (issue.getReturnDate() !=null) { %>
                                                    <span class="returned">Returned</span>
                                                    <% } else if (overdue) { %>
                                                        <span class="overdue">Overdue</span>
                                                        <% } else { %>
                                                            Issued
                                                            <% } %>
                                            </td>
                                            <td><% if ("PENDING".equals(issue.getReturnStatus())) { %>
                                                <form method="post" action="<%=request.getContextPath()%>/admin/approve-return"
                                                      onsubmit="return confirm('Confirm the physical book was received?');">
                                                    <input type="hidden" name="issueId" value="<%=issue.getId()%>">
                                                    <button type="submit">Accept Return</button>
                                                </form>
                                                <% } else { %>Done<% } %></td>
                                        </tr>
                                        <% } } else { %>
                                            <tr>
                                                <td colspan="8">No issue records found.</td>
                                            </tr>
                                            <% } %>
                                </tbody>
                            </table>
                        </div>

                        <p>
                            <a href="<%= request.getContextPath() %>/admin/dashboard.jsp">
                                 Back to Admin Dashboard
                            </a>
                        </p>
                    </div>
                </body>

                </html>