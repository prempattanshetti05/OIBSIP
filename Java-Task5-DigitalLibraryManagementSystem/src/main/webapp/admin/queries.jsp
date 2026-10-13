<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Query" %>

        <% List<Query> queries =
            (List<Query>) request.getAttribute("queries");

                String result = request.getParameter("result");
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Member Queries - Digital Library</title>
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
                            vertical-align: top;
                        }

                        th {
                            background: #f3f4f6;
                        }

                        button {
                            padding: 8px 12px;
                            background: #15803d;
                            color: white;
                            border: 0;
                            border-radius: 5px;
                            cursor: pointer;
                        }

                        .open {
                            color: #b45309;
                            font-weight: bold;
                        }

                        .resolved {
                            color: #15803d;
                            font-weight: bold;
                        }

                        .message {
                            max-width: 350px;
                            overflow-wrap: anywhere;
                            white-space: pre-wrap;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <h1>Member Queries</h1>

                        <% if ("resolved".equals(result)) { %>
                            <p style="background:#dcfce7;padding:12px">
                                Query marked as resolved.
                            </p>
                            <% } else if ("error".equals(result)) { %>
                                <p style="background:#fee2e2;padding:12px">
                                    Unable to update the query.
                                </p>
                                <% } %>

                                    <div style="overflow-x:auto">
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Member</th>
                                                    <th>Subject</th>
                                                    <th>Message</th>
                                                    <th>Submitted</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% if (queries !=null && !queries.isEmpty()) { for (Query query :
                                                    queries) { %>
                                                    <tr>
                                                        <td>
                                                            <%= query.getUserName() %>
                                                        </td>
                                                        <td>
                                                            <%= query.getSubject() %>
                                                        </td>
                                                        <td class="message">
                                                            <%= query.getMessage() %>
                                                        </td>
                                                        <td>
                                                            <%= query.getCreatedAt() %>
                                                        </td>
                                                        <td>
                                                            <% if ("OPEN".equals(query.getStatus())) { %>
                                                                <span class="open">Open</span>
                                                                <% } else { %>
                                                                    <span class="resolved">Resolved</span>
                                                                    <% } %>
                                                        </td>
                                                        <td>
                                                            <% if ("OPEN".equals(query.getStatus())) { %>
                                                                <form method="post"
                                                                    action="<%= request.getContextPath() %>/admin/resolve-query">
                                                                    <input type="hidden" name="queryId"
                                                                        value="<%= query.getId() %>">
                                                                    <button type="submit">Resolve</button>
                                                                </form>
                                                                <% } else { %>
                                                                    Done
                                                                    <% } %>
                                                        </td>
                                                    </tr>
                                                    <% } } else { %>
                                                        <tr>
                                                            <td colspan="6">No queries have been submitted.</td>
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