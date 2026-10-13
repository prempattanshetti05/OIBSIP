
<%@ page import="com.library.model.Book" %>

    <% Book book=(Book) request.getAttribute("book"); String error=request.getParameter("error"); %>

        <!DOCTYPE html>
        <html>

        <head>

            <meta charset="UTF-8">

            <title>Edit Book - Digital Library</title>

            <link rel="stylesheet" href="../css/style.css?v=2">

        </head>

        <body>

            <div class="dashboard-container">

                <h1>Edit Book</h1>


                <!-- Error Messages -->

                <% if ("empty".equals(error)) { %>

                    <p style="color: red;">
                        Please fill all fields.
                    </p>

                    <% } else if ("quantity".equals(error)) { %>

                        <p style="color: red;">
                            Available quantity cannot be greater than total quantity.
                        </p>

                        <% } else if ("failed".equals(error)) { %>

                            <p style="color: red;">
                                Book could not be updated.
                            </p>

                            <% } else if ("invalid".equals(error)) { %>

                                <p style="color: red;">
                                    Invalid book information.
                                </p>

                                <% } %>


                                    <% if (book !=null) { %>

                                        <form action="edit-book" method="post">

                                            <!-- Book ID -->

                                            <input type="hidden" name="id" value="<%= book.getId() %>">


                                            <!-- Title -->

                                            <div class="form-group">

                                                <label>Book Title</label>

                                                <input type="text" name="title" value="<%= book.getTitle() %>" required>

                                            </div>


                                            <!-- Author -->

                                            <div class="form-group">

                                                <label>Author</label>

                                                <input type="text" name="author" value="<%= book.getAuthor() %>"
                                                    required>

                                            </div>


                                            <!-- ISBN -->

                                            <div class="form-group">

                                                <label>ISBN</label>

                                                <input type="text" name="isbn" value="<%= book.getIsbn() %>" required>

                                            </div>


                                            <!-- Category -->

                                            <div class="form-group">

                                                <label>Category</label>

                                                <input type="text" name="category" value="<%= book.getCategory() %>"
                                                    required>

                                            </div>


                                            <!-- Total Quantity -->

                                            <div class="form-group">

                                                <label>Total Quantity</label>

                                                <input type="number" name="quantity" value="<%= book.getQuantity() %>"
                                                    min="1" required>

                                            </div>


                                            <!-- Available Quantity -->

                                            <div class="form-group">

                                                <label>Available Quantity</label>

                                                <input type="number" name="availableQuantity"
                                                    value="<%= book.getAvailableQuantity() %>" min="0"
                                                    max="<%= book.getQuantity() %>" required>

                                            </div>


                                            <!-- Buttons -->

                                            <button type="submit">
                                                Update Book
                                            </button>

                                            <a href="books">
                                                Cancel
                                            </a>

                                        </form>

                                        <% } else { %>

                                            <p style="color: red;">
                                                Book not found.
                                            </p>

                                            <a href="books">
                                                 Back to Books
                                            </a>

                                            <% } %>

            </div>

        </body>

        </html>