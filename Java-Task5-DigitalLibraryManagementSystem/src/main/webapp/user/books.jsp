<%@ page import="java.util.List" %>
    <%@ page import="com.library.model.Book" %>

        <% List<Book> books = (List<Book>) request.getAttribute("books");

                String success = request.getParameter("success");
                String error = request.getParameter("error");

                String search = request.getParameter("search");
                String category = request.getParameter("category");

                if (search == null) search = "";
                if (category == null) category = "";
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Browse Books - Digital Library</title>

                    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">

                    <style>
                        .search-form {
                            display: flex;
                            gap: 12px;
                            flex-wrap: wrap;
                            margin: 20px 0;
                        }

                        .search-form input {
                            padding: 10px;
                            border: 1px solid #cbd5e1;
                            border-radius: 6px;
                            flex: 1;
                            min-width: 180px;
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
                        }

                        .available {
                            color: #15803d;
                            font-weight: bold;
                        }

                        .unavailable {
                            color: #dc2626;
                            font-weight: bold;
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

                        .action-form {
                            display: inline;
                        }

                        button {
                            cursor: pointer;
                        }
                    </style>
                </head>

                <body>

                    <div class="page-container">

                        <div class="topbar">
                            <h1>Library Catalogue</h1>

                            <a class="btn danger" href="<%= request.getContextPath() %>/logout">
                                Logout
                            </a>
                        </div>

                        <p>Search for books by title, author, or category.</p>

                        <!-- Search and category filter -->
                        <form action="<%= request.getContextPath() %>/user/books" method="get" class="search-form">

                            <input type="text" name="search" placeholder="Search title or author" value="<%= search %>">

                            <input type="text" name="category" placeholder="Category (e.g. Java)"
                                value="<%= category %>">

                            <button type="submit">Search</button>

                            <a class="btn secondary" href="<%= request.getContextPath() %>/user/books">
                                Clear
                            </a>
                        </form>

                        <!-- Success messages -->
                        <% if ("issued".equals(success)) { %>
                            <div class="message success">
                                Book issued successfully! Please check your due date in My Books.
                            </div>
                            <% } else if ("reserved".equals(success)) { %>
                                <div class="message success">
                                    Book reservation submitted successfully!
                                </div>
                                <% } %>

                                    <!-- Error messages -->
                                    <% if ("issue".equals(error)) { %>
                                        <div class="message error">
                                            Unable to issue this book. It may be unavailable or an error occurred.
                                        </div>
                                        <% } else if ("reserve".equals(error)) { %>
                                            <div class="message error">
                                                Unable to reserve the book. Please try again.
                                            </div>
                                            <% } else if ("invalid".equals(error)) { %>
                                                <div class="message error">
                                                    Invalid book selection. Please try again.
                                                </div>
                                                <% } %>

                                                    <!-- Book catalogue -->
                                                    <div class="table-container">
                                                        <table>
                                                            <thead>
                                                                <tr>
                                                                    <th>Title</th>
                                                                    <th>Author</th>
                                                                    <th>ISBN</th>
                                                                    <th>Category</th>
                                                                    <th>Available</th>
                                                                    <th>Action</th>
                                                                </tr>
                                                            </thead>

                                                            <tbody>

                                                                <% if (books !=null && !books.isEmpty()) { %>

                                                                    <% for (Book book : books) { %>
                                                                        <tr>
                                                                            <td>
                                                                                <%= book.getTitle() %>
                                                                            </td>
                                                                            <td>
                                                                                <%= book.getAuthor() %>
                                                                            </td>
                                                                            <td>
                                                                                <%= book.getIsbn() %>
                                                                            </td>
                                                                            <td>
                                                                                <%= book.getCategory() %>
                                                                            </td>

                                                                            <td>
                                                                                <% if (book.getAvailableQuantity()> 0) {
                                                                                    %>
                                                                                    <span class="available">
                                                                                        <%= book.getAvailableQuantity()
                                                                                            %> available
                                                                                    </span>
                                                                                    <% } else { %>
                                                                                        <span class="unavailable">
                                                                                            Out of stock
                                                                                        </span>
                                                                                        <% } %>
                                                                            </td>

                                                                            <td>
                                                                                <% if (book.getAvailableQuantity()> 0) {
                                                                                    %>

                                                                                    <!-- Issue book -->
                                                                                    <form class="action-form"
                                                                                        action="<%= request.getContextPath() %>/user/issue-book"
                                                                                        method="post">

                                                                                        <input type="hidden"
                                                                                            name="bookId"
                                                                                            value="<%= book.getId() %>">

                                                                                        <button
                                                                                            type="submit">Issue</button>
                                                                                    </form>

                                                                                    <% } else { %>

                                                                                        <!-- Reserve unavailable book -->
                                                                                        <form class="action-form"
                                                                                            action="<%= request.getContextPath() %>/user/reserve-book"
                                                                                            method="post">

                                                                                            <input type="hidden"
                                                                                                name="bookId"
                                                                                                value="<%= book.getId() %>">

                                                                                            <button
                                                                                                type="submit">Reserve</button>
                                                                                        </form>

                                                                                        <% } %>
                                                                            </td>
                                                                        </tr>
                                                                        <% } %>

                                                                            <% } else { %>

                                                                                <tr>
                                                                                    <td colspan="6"
                                                                                        style="text-align:center; padding:25px;">
                                                                                        No books found. Try another
                                                                                        search.
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
                