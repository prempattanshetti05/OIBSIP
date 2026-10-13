
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Reservation" %>
        <%@ page import="java.text.SimpleDateFormat" %>

            <% List<Reservation> reservations =
                (List<Reservation>) request.getAttribute("reservations");

                    String success = request.getParameter("success");
                    String error = request.getParameter("error");

                    SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                    %>

                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>My Reservations - Digital Library</title>

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

                            .pending {
                                color: #b45309;
                                font-weight: bold;
                            }

                            .fulfilled {
                                color: #15803d;
                                font-weight: bold;
                            }

                            .cancelled {
                                color: #dc2626;
                                font-weight: bold;
                            }

                            .cancel-button {
                                padding: 8px 12px;
                                background: #dc2626;
                                color: white;
                                border: none;
                                border-radius: 5px;
                                cursor: pointer;
                            }

                            .cancel-button:hover {
                                background: #b91c1c;
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
                                <h1>My Reservations</h1>

                                <a class="btn danger" href="<%= request.getContextPath() %>/logout">
                                    Logout
                                </a>
                            </div>

                            <p>View the status of your reserved books.</p>

                            <% if ("cancelled".equals(success)) { %>
                                <div class="message success">
                                    Reservation cancelled successfully.
                                </div>
                                <% } %>

                                    <% if ("cancel".equals(error)) { %>
                                        <div class="message error">
                                            Unable to cancel the reservation. Please try again.
                                        </div>
                                        <% } else if ("invalid".equals(error)) { %>
                                            <div class="message error">
                                                Invalid reservation selection.
                                            </div>
                                            <% } %>

                                                <div class="table-container">
                                                    <table>
                                                        <thead>
                                                            <tr>
                                                                <th>Book Title</th>
                                                                <th>Reservation Date</th>
                                                                <th>Status</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>

                                                            <% if (reservations !=null && !reservations.isEmpty()) { %>

                                                                <% for (Reservation reservation : reservations) { %>

                                                                    <tr>
                                                                        <td>
                                                                            <%= reservation.getBookTitle() %>
                                                                        </td>

                                                                        <td>
                                                                            <%= reservation.getReservationDate() !=null
                                                                                ? dateFormat.format(
                                                                                reservation.getReservationDate())
                                                                                : "N/A" %>
                                                                        </td>

                                                                        <td>
                                                                            <% if ("PENDING".equals(
                                                                                reservation.getStatus())) { %>

                                                                                <span class="pending">Pending</span>

                                                                                <% } else if ("FULFILLED".equals(
                                                                                    reservation.getStatus())) { %>

                                                                                    <span
                                                                                        class="fulfilled">Fulfilled</span>

                                                                                    <% } else { %>

                                                                                        <span
                                                                                            class="cancelled">Cancelled</span>

                                                                                        <% } %>
                                                                        </td>

                                                                        <td>
                                                                            <% if ("PENDING".equals(
                                                                                reservation.getStatus())) { %>

                                                                                <form method="post"
                                                                                    action="<%= request.getContextPath() %>/user/cancel-reservation"
                                                                                    onsubmit="return confirm('Cancel this reservation?');">

                                                                                    <input type="hidden"
                                                                                        name="reservationId"
                                                                                        value="<%= reservation.getId() %>">

                                                                                    <button type="submit"
                                                                                        class="cancel-button">
                                                                                        Cancel
                                                                                    </button>
                                                                                </form>

                                                                                <% } else { %>
                                                                                    Done
                                                                                    <% } %>
                                                                        </td>
                                                                    </tr>

                                                                    <% } %>

                                                                        <% } else { %>

                                                                            <tr>
                                                                                <td colspan="4" class="empty-message">
                                                                                    You have no reservations yet.
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
                    