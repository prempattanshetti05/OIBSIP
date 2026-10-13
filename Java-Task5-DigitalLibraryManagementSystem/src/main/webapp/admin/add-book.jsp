
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <% String error=request.getParameter("error"); %>

        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Add Book - Digital Library</title>
            <link rel="stylesheet" href="../css/style.css?v=2">
        </head>

        <body>

            <div class="dashboard-container">

                <h1>Add New Book</h1>

                <% if ("empty".equals(error)) { %>
                    <p>Please fill all fields.</p>
                    <% } else if ("quantity".equals(error)) { %>
                        <p>Please enter a valid quantity.</p>
                        <% } else if ("failed".equals(error)) { %>
                            <p>Book could not be added.</p>
                            <% } %>

                                <form action="add-book" method="post">

                                    <div class="form-group">
                                        <label>Book Title</label>
                                        <input type="text" name="title" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Author</label>
                                        <input type="text" name="author" required>
                                    </div>

                                    <div class="form-group">
                                        <label>ISBN</label>
                                        <input type="text" name="isbn" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Category</label>
                                        <input type="text" name="category" required>
                                    </div>

                                    <div class="form-group">
                                        <label>Quantity</label>
                                        <input type="number" name="quantity" min="1" required>
                                    </div>

                                    <button type="submit">Add Book</button>

                                </form>

                                <br>

                                <a href="dashboard.jsp"> Back to Dashboard</a>

            </div>

        </body>

        </html>