
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Fine" %>

        <% List<Fine> fines = (List<Fine>) request.getAttribute("fines");
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>My Fines - Digital Library</title>
                    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
                    <style>
                        .container {
                            width: 90%;
                            max-width: 1100px;
                            margin: 40px auto;
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
                        <h1>My Fines</h1>
                        <p>Review fines associated with your borrowed books.</p>

                        <table>
                            <thead>
                                <tr>
                                    <th>Book</th>
                                    <th>Fine Amount</th>
                                    <th>Created At</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (fines !=null && !fines.isEmpty()) { for (Fine fine : fines) { %>
                                    <tr>
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
                                    </tr>
                                    <% } } else { %>
                                        <tr>
                                            <td colspan="4">You have no fines.</td>
                                        </tr>
                                        <% } %>
                            </tbody>
                        </table>

                        <p>
                            <a href="<%= request.getContextPath() %>/user/dashboard.jsp">
                                 Back to Dashboard
                            </a>
                        </p>
                    </div>
                </body>

                </html>