<%@ page import="java.util.List" %>

    <% List<String[]> members =
        (List<String[]>) request.getAttribute("members");
            %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Library Members - Digital Library</title>
                <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
                <style>
                    .container {
                        width: 90%;
                        max-width: 1100px;
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
                </style>
            </head>

            <body>
                <div class="container">
                    <h1>Library Members</h1>
                    <p>Registered user accounts in the digital library.</p>

                    <div style="overflow-x:auto">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Registered At</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (members !=null && !members.isEmpty()) { for (String[] member : members) { %>
                                    <tr>
                                        <td>
                                            <%= member[0] %>
                                        </td>
                                        <td>
                                            <%= member[1] %>
                                        </td>
                                        <td>
                                            <%= member[2] %>
                                        </td>
                                        <td>
                                            <%= member[3] %>
                                        </td>
                                    </tr>
                                    <% } } else { %>
                                        <tr>
                                            <td colspan="4">No registered members found.</td>
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