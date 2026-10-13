
<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Book" %>

        <% List<Book> books = (List<Book>) request.getAttribute("books");

                String success = request.getParameter("success");
                String error = request.getParameter("error");
                %>

                <!DOCTYPE html>
                <html>

                <head>

                    <meta charset="UTF-8">

                    <title>Manage Books - Digital Library</title>

                    <link rel="stylesheet" href="../css/style.css?v=2">

                    <style>
                        .page-container {
                            width: 90%;
                            max-width: 1200px;
                            margin: 40px auto;
                        }

                        .page-header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-bottom: 25px;
                        }

                        .page-header h1 {
                            margin: 0;
                        }

                        .add-button {
                            display: inline-block;
                            padding: 10px 18px;
                            background: #2563eb;
                            color: white;
                            text-decoration: none;
                            border-radius: 6px;
                        }

                        .add-button:hover {
                            background: #1d4ed8;
                        }

                        .success-message {
                            background: #dcfce7;
                            color: #166534;
                            padding: 12px;
                            border-radius: 6px;
                            margin-bottom: 20px;
                        }

                        .error-message {
                            background: #fee2e2;
                            color: #991b1b;
                            padding: 12px;
                            border-radius: 6px;
                            margin-bottom: 20px;
                        }

                        .table-container {
                            overflow-x: auto;
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                            background: white;
                        }

                        th,
                        td {
                            padding: 14px;
                            border-bottom: 1px solid #ddd;
                            text-align: left;
                        }

                        th {
                            background: #f3f4f6;
                            font-weight: 600;
                        }

                        tr:hover {
                            background: #f9fafb;
                        }

                        .available {
                            color: #15803d;
                            font-weight: bold;
                        }

                        .unavailable {
                            color: #dc2626;
                            font-weight: bold;
                        }

                        .empty-message {
                            text-align: center;
                            padding: 30px;
                            color: #666;
                        }

                        .back-link {
                            display: inline-block;
                            margin-top: 20px;
                            text-decoration: none;
                        }

                        .edit-button {
                            display: inline-block;
                            padding: 7px 12px;
                            background: #2563eb;
                            color: white;
                            text-decoration: none;
                            border-radius: 5px;
                            margin-right: 5px;
                        }

                        .edit-button:hover {
                            background: #1d4ed8;
                        }

                        .delete-button {
                            padding: 7px 12px;
                            background: #dc2626;
                            color: white;
                            border: none;
                            border-radius: 5px;
                            cursor: pointer;
                        }

                        .delete-button:hover {
                            background: #b91c1c;
                        }

                        .inline-form {
                            display: inline;
                        }
                    </style>

                    <script>

                        function confirmDelete() {

                            return confirm(
                                "Are you sure you want to delete this book?"
                            );

                        }

                    </script>

                </head>


                <body>


                    <div class="page-container">


                        <!-- PAGE HEADER -->

                        <div class="page-header">

                            <h1>
                                Manage Books
                            </h1>

                            <a href="add-book.jsp" class="add-button">
                                + Add Book
                            </a>

                        </div>


                        <!-- SUCCESS MESSAGE -->

                        <% if ("added".equals(success)) { %>

                            <div class="success-message">
                                Book added successfully!
                            </div>

                            <% } else if ("updated".equals(success)) { %>

                                <div class="success-message">
                                    Book updated successfully!
                                </div>

                                <% } else if ("deleted".equals(success)) { %>

                                    <div class="success-message">
                                        Book deleted successfully!
                                    </div>

                                    <% } %>


                                        <!-- ERROR MESSAGE -->

                                        <% if ("delete".equals(error)) { %>

                                            <div class="error-message">
                                                Book could not be deleted.
                                            </div>

                                            <% } else if ("notfound".equals(error)) { %>

                                                <div class="error-message">
                                                    Book not found.
                                                </div>

                                                <% } else if ("invalid".equals(error)) { %>

                                                    <div class="error-message">
                                                        Invalid book information.
                                                    </div>

                                                    <% } else if ("quantity".equals(error)) { %>

                                                        <div class="error-message">
                                                            Invalid quantity.
                                                        </div>

                                                        <% } %>


                                                            <!-- BOOK TABLE -->

                                                            <div class="table-container">

                                                                <table>

                                                                    <thead>

                                                                        <tr>

                                                                            <th>ID</th>

                                                                            <th>Title</th>

                                                                            <th>Author</th>

                                                                            <th>ISBN</th>

                                                                            <th>Category</th>

                                                                            <th>Total Quantity</th>

                                                                            <th>Available</th>

                                                                            <th>Status</th>

                                                                            <th>Actions</th>

                                                                        </tr>

                                                                    </thead>


                                                                    <tbody>


                                                                        <% if (books !=null && !books.isEmpty()) { %>


                                                                            <% for (Book book : books) { %>

                                                                                <tr>


                                                                                    <!-- ID -->

                                                                                    <td>
                                                                                        <%= book.getId() %>
                                                                                    </td>


                                                                                    <!-- TITLE -->

                                                                                    <td>
                                                                                        <%= book.getTitle() %>
                                                                                    </td>


                                                                                    <!-- AUTHOR -->

                                                                                    <td>
                                                                                        <%= book.getAuthor() %>
                                                                                    </td>


                                                                                    <!-- ISBN -->

                                                                                    <td>
                                                                                        <%= book.getIsbn() %>
                                                                                    </td>


                                                                                    <!-- CATEGORY -->

                                                                                    <td>
                                                                                        <%= book.getCategory() %>
                                                                                    </td>


                                                                                    <!-- TOTAL QUANTITY -->

                                                                                    <td>
                                                                                        <%= book.getQuantity() %>
                                                                                    </td>


                                                                                    <!-- AVAILABLE QUANTITY -->

                                                                                    <td>
                                                                                        <%= book.getAvailableQuantity()
                                                                                            %>
                                                                                    </td>


                                                                                    <!-- STATUS -->

                                                                                    <td>

                                                                                        <% if
                                                                                            (book.getAvailableQuantity()>
                                                                                            0) { %>

                                                                                            <span class="available">
                                                                                                Available
                                                                                            </span>

                                                                                            <% } else { %>

                                                                                                <span
                                                                                                    class="unavailable">
                                                                                                    Out of Stock
                                                                                                </span>

                                                                                                <% } %>

                                                                                    </td>


                                                                                    <!-- ACTIONS -->

                                                                                    <td>


                                                                                        <!-- EDIT -->

                                                                                        <a href="edit-book?id=<%= book.getId() %>"
                                                                                            class="edit-button">
                                                                                            Edit
                                                                                        </a>


                                                                                        <!-- DELETE -->

                                                                                        <form action="delete-book"
                                                                                            method="post"
                                                                                            class="inline-form"
                                                                                            onsubmit="return confirmDelete();">

                                                                                            <input type="hidden"
                                                                                                name="id"
                                                                                                value="<%= book.getId() %>">

                                                                                            <button type="submit"
                                                                                                class="delete-button">
                                                                                                Delete
                                                                                            </button>

                                                                                        </form>


                                                                                    </td>


                                                                                </tr>

                                                                                <% } %>


                                                                                    <% } else { %>


                                                                                        <tr>

                                                                                            <td colspan="9"
                                                                                                class="empty-message">

                                                                                                No books found in the
                                                                                                library.

                                                                                            </td>

                                                                                        </tr>


                                                                                        <% } %>


                                                                    </tbody>

                                                                </table>

                                                            </div>


                                                            <!-- BACK -->

                                                            <a href="dashboard.jsp" class="back-link">

                                                                 Back to Admin Dashboard

                                                            </a>


                    </div>


                </body>

                </html>