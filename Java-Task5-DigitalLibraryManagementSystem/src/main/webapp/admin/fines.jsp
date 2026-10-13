
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Fine" %>

        <% List<Fine> fines = (List<Fine>) request.getAttribute("fines");
                String result = request.getParameter("success");
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Manage Fines - Digital Library</title>
                    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
                    <style>
                        .container {
                            width: 95%;
                            max-width: 1200px;
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

                        button {
                            background: #15803d;
                            color: white;
                            padding: 8px 12px;
                            border: 0;
                            border-radius: 5px;
                            cursor: pointer;
                        }

                        .message {
                            margin: 15px 0;
                            padding: 12px;
                            border-radius: 5px;
                        }

                        .paid {
                            color: #15803d;
                            font-weight: bold;
                        }

                        .pending {
                            color: #dc2626;
                            font-weight: bold;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <h1>Manage Fines</h1>

                        <% if ("paid".equals(result)) { %>
                            <p class="message" style="background:#dcfce7">
                                Fine marked as paid.
                            </p>
                            <% } else if ("error".equals(result)) { %>
                                <p class="message" style="background:#fee2e2">
                                    Unable to update the fine.
                                </p>
                                <% } %>

                                    <table>
                                        <thead>
                                            <tr>
                                                <th>User</th>
                                                <th>Book</th>
                                                <th>Amount</th>
                                                <th>Created At</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (fines !=null && !fines.isEmpty()) { for (Fine fine : fines) { %>
                                                <tr>
                                                    <td>
                                                        <%= fine.getUserName() %>
                                                    </td>
                                                    <td>
                                                        <%= fine.getBookTitle() %>
                                                    </td>
                                                    <td>₹<%= String.format("%.2f", fine.getAmount()) %>
                                                    </td>
                                                    <td>
                                                        <%= fine.getCreatedAt() %>
                                                    </td>
                                                    <td>
                                                        <% if (fine.isPaid()) { %>
                                                            <span class="paid">Paid</span>
                                                            <% } else { %>
                                                                <span class="pending">Pending</span>
                                                                <% } %>
                                                    </td>
                                                    <td>
                                                        <% if (!fine.isPaid()) { %>
                                                            <form method="post"
                                                                action="<%= request.getContextPath() %>/admin/mark-fine-paid">
                                                                <input type="hidden" name="fineId"
                                                                    value="<%= fine.getId() %>">
                                                                <button type="submit">Mark Paid</button>
                                                            </form>
                                                            <% } else { %>
                                                                —
                                                                <% } %>
                                                    </td>
                                                </tr>
                                                <% } } else { %>
                                                    <tr>
                                                        <td colspan="6">No fines recorded.</td>
                                                    </tr>
                                                    <% } %>
                                        </tbody>
                                    </table>

                                    <p>
                                        <a href="<%= request.getContextPath() %>/admin/dashboard.jsp">
                                             Back to Admin Dashboard
                                        </a>
                                    </p>
                    </div>
                </body>

                </html>