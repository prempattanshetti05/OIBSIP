
<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8">
    <title>Contact Library - Digital Library</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css?v=2">
    <style>
        .container {
            max-width: 650px;
            margin: 40px auto;
            padding: 20px;
        }

        label {
            display: block;
            margin-top: 15px;
        }

        input,
        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            box-sizing: border-box;
        }

        textarea {
            min-height: 150px;
        }

        button {
            margin-top: 15px;
            padding: 10px 18px;
            cursor: pointer;
        }

        .message {
            padding: 12px;
            margin: 15px 0;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Contact Library</h1>
        <p>Send a question or message to the library administrator.</p>

        <% if ("sent".equals(request.getParameter("result"))) { %>
            <p class="message" style="background:#dcfce7">
                Your query was submitted successfully.
            </p>
            <% } else if ("error".equals(request.getParameter("result")) || "invalid"
                .equals(request.getParameter("error"))) { %>
                <p class="message" style="background:#fee2e2">
                    Unable to submit your query. Check the fields and try again.
                </p>
                <% } %>

                    <form method="post" action="<%= request.getContextPath() %>/user/contact">

                        <label for="subject">Subject</label>
                        <input id="subject" type="text" name="subject" maxlength="200" required>

                        <label for="message">Message</label>
                        <textarea id="message" name="message" maxlength="5000" required></textarea>

                        <button type="submit">Send Query</button>
                    </form>

                    <p>
                        <a href="<%= request.getContextPath() %>/user/dashboard.jsp">
                             Back to Dashboard
                        </a>
                    </p>
    </div>
</body>

</html>