# Digital Library Management System

A Java-based web application for managing a library catalogue, members, book issues and return requests, reservations, fines, and user queries. It was developed as a web application using Java Servlets, JSP, JDBC, MySQL, Maven, and Apache Tomcat 9.

> **Project:** OASIS INFOBYTE Java Development Internship — Task 5  
> **Application name:** DigitalLibraryManagementSystem

## Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Database](#database)
- [Setup](#setup)
- [Build and Deploy](#build-and-deploy)
- [Application Workflows](#application-workflows)
- [Testing Checklist](#testing-checklist)
- [Troubleshooting](#troubleshooting)
- [Security Notes](#security-notes)
- [Future Improvements](#future-improvements)

## Overview

The Digital Library Management System provides separate workflows for administrators and students/users.

Administrators manage books, review issued-book records, view registered members, manage fines, review reservations, and resolve user queries. Students/users can register and log in, browse and search books, issue available books, request returns, reserve unavailable books, view fines, and contact the administrator.

The application uses JSP pages for the interface, Java Servlets for request handling, DAO classes for database operations, model classes for application data, and JDBC to connect to MySQL.

## Features

### Administrator

- Admin login and dashboard.
- Add, edit, and delete catalogue books.
- View issued-book records, member details, due dates, and return status.
- View registered library members.
- View fines and mark fines as paid.
- View and resolve user queries.
- Review return requests and approve them.
- View reservations and fulfill eligible pending reservations when a copy is available.

### Student/User

- Register and log in.
- Browse and search books.
- View book availability.
- Issue a book when copies are available.
- View current loans and due dates.
- Request a return; the request is processed after administrator approval.
- Reserve an unavailable book and view reservation status.
- Cancel an eligible pending reservation.
- View fines.
- Submit a query to the administrator.
- Log out and end the session.

### Interface

- Separate admin and user dashboards.
- Shared stylesheet at `src/main/webapp/css/style.css`.
- HTML, CSS, and JSP-based pages.
- UTF-8 page encoding.

## Technology Stack

| Technology | Use |
|---|---|
| Java | Application logic |
| Java Servlets (`javax.servlet`) | HTTP request handling |
| JSP | Dynamic web pages |
| JDBC | Database access |
| MySQL | Persistent data storage |
| MySQL Connector/J 9.4.0 | MySQL JDBC driver |
| Maven | Dependency management and WAR build |
| Apache Tomcat 9 | Web application server |
| HTML5 and CSS3 | User interface |
| Visual Studio Code | Development environment |

The `pom.xml` specifies Java source/target version **25**, Servlet API **4.0.1** with `provided` scope, MySQL Connector/J **9.4.0**, and WAR packaging. Use a JDK compatible with this configuration. The project uses `javax.servlet`, so Tomcat 9 is the intended servlet container; Tomcat 10+ uses the `jakarta.servlet` namespace and requires migration.

## Requirements

Install and configure:

- JDK compatible with the Java version in `pom.xml`.
- Apache Maven.
- MySQL Server.
- MySQL Workbench (recommended).
- Apache Tomcat 9.
- Visual Studio Code or another Java IDE.

Check installations:

```bash
java -version
javac -version
mvn -version
```

## Project Structure

```text
Java-Task5-DigitalLibraryManagementSystem/
├── .gitignore
├── pom.xml
├── README.md
├── database/
│   └── digital_library.sql
└── src/
    └── main/
        ├── java/
        │   └── com/library/
        │       ├── controller/
        │       │   ├── LoginServlet.java
        │       │   ├── RegisterServlet.java
        │       │   ├── LogoutServlet.java
        │       │   ├── AddBookServlet.java
        │       │   ├── BooksServlet.java
        │       │   ├── EditBookServlet.java
        │       │   ├── DeleteBookServlet.java
        │       │   ├── UserBooksServlet.java
        │       │   ├── IssueBookServlet.java
        │       │   ├── MyBooksServlet.java
        │       │   ├── ReturnBookServlet.java
        │       │   ├── AdminApproveReturnServlet.java
        │       │   ├── ReserveBookServlet.java
        │       │   ├── UserReservationsServlet.java
        │       │   ├── AdminReservationsServlet.java
        │       │   ├── AdminFulfillReservationServlet.java
        │       │   ├── UserFinesServlet.java
        │       │   ├── AdminFinesServlet.java
        │       │   ├── MarkFinePaidServlet.java
        │       │   ├── AdminIssuedBooksServlet.java
        │       │   ├── AdminMembersServlet.java
        │       │   ├── ContactServlet.java
        │       │   ├── AdminQueriesServlet.java
        │       │   └── ResolveQueryServlet.java
        │       ├── dao/
        │       │   ├── UserDAO.java
        │       │   ├── BookDAO.java
        │       │   ├── IssueDAO.java
        │       │   ├── ReservationDAO.java
        │       │   ├── FineDAO.java
        │       │   └── QueryDAO.java
        │       ├── model/
        │       │   ├── User.java
        │       │   ├── Book.java
        │       │   ├── Issue.java
        │       │   ├── Reservation.java
        │       │   ├── Fine.java
        │       │   └── Query.java
        │       └── util/
        │           └── DBConnection.java
        └── webapp/
            ├── index.jsp
            ├── css/style.css
            ├── auth/
            ├── admin/
            └── user/
```

The tree highlights the main files; use the actual files in the repository as the source of truth.

## Database

The SQL script is located at:

```text
database/digital_library.sql
```

The application uses the `digital_library` database and these tables:

| Table | Purpose |
|---|---|
| `users` | User profiles, email, password field, and role (`USER` or `ADMIN`) |
| `books` | Book metadata, total quantity, and available quantity |
| `issues` | Issue date, due date, and return date |
| `reservations` | Reservation date and status (`PENDING`, `FULFILLED`, `CANCELLED`) |
| `fines` | Fine amount and payment status |
| `queries` | User messages and query status |
| `return_requests` | Return requests, approval status, and review timestamps |

The supplied SQL script creates the database tables, including `return_requests`. Back up any existing database before running schema scripts. Do not drop or recreate a database that contains records you want to keep.

### Database relationships

- `issues.user_id` references `users.id`.
- `issues.book_id` references `books.id`.
- `reservations.user_id` references `users.id`.
- `reservations.book_id` references `books.id`.
- `fines.issue_id` references `issues.id`.
- `fines.user_id` references `users.id`.
- `queries.user_id` references `users.id`.
- `return_requests.issue_id` references `issues.id`.
- `return_requests.user_id` references `users.id`.

## Setup

### 1. Open the project

Open the extracted project folder—the directory containing `pom.xml`—in VS Code.

### 2. Create or verify the database

1. Start MySQL Server.
2. Open MySQL Workbench.
3. Open `database/digital_library.sql`.
4. Review the script and execute it against your MySQL server.
5. Confirm that the database and tables exist.

If you already have a working database, back it up first and inspect the script before running it. The supplied script begins with `CREATE DATABASE digital_library;`, which can fail if the database already exists. Do not rerun it blindly on an existing database.

### 3. Configure the database connection

Edit:

```text
src/main/java/com/library/util/DBConnection.java
```

The current source uses this JDBC URL:

```text
jdbc:mysql://localhost:3306/digital_library
```

Set the username and password to match your local MySQL installation. The uploaded source currently contains a local root-account password in `DBConnection.java`; **replace it with your own local configuration and never publish real credentials**. For a public deployment, use environment variables or a protected configuration mechanism.

### 4. Check the admin account

New registrations use the default `USER` role according to the database schema. Ensure you have an administrator account before testing admin-only features. Follow your project's approved local account setup process and do not publish administrator credentials.

## Build and Deploy

### 1. Build the WAR

From the directory containing `pom.xml`, run:

```bash
mvn clean package
```

The expected Maven result is `BUILD SUCCESS`. The configured WAR name is:

```text
target/DigitalLibraryManagementSystem.war
```

### 2. Deploy to Tomcat 9

1. Start Tomcat 9.
2. Deploy `target/DigitalLibraryManagementSystem.war` through the VS Code Servers panel or copy it to Tomcat's `webapps` directory.
3. Remove/stop an older deployment first if necessary so that Tomcat does not keep serving stale files.
4. Start or restart the application.
5. Open:

```text
http://localhost:8080/DigitalLibraryManagementSystem/
```

The URL assumes Tomcat runs on port `8080` and uses the configured WAR name. Change the port or context path if your setup differs.

### 3. Verify CSS

The shared stylesheet is:

```text
src/main/webapp/css/style.css
```

You can check whether Tomcat serves it by opening:

```text
http://localhost:8080/DigitalLibraryManagementSystem/css/style.css
```

If the browser shows 404, check the deployed WAR and stylesheet path. If styling looks unchanged after deployment, confirm the new WAR is deployed and perform a hard refresh (`Ctrl+F5`).

## Application Workflows

### Book issue

1. A student logs in and opens the catalogue.
2. The student selects an available book.
3. The application records the issue and due date.
4. Available quantity decreases when the issue is successful.

The current `IssueDAO` sets the due date to **14 days after the issue date**.

### Return approval

1. A student requests a return from the My Books page.
2. The request is recorded as pending.
3. An administrator reviews the issued-book record and approves the return.
4. After approval, the issue is marked returned and available quantity is updated.
5. If the return is overdue, the fine logic can create a fine record.

### Reservations

1. A student can request a reservation when the book has no available copies.
2. The reservation is stored as `PENDING`.
3. An administrator reviews reservations.
4. The admin fulfillment action is intended to allocate an available copy to the eligible pending reservation and mark it `FULFILLED`.

### Fines

The DAO configures an overdue fine rate of **₹5 per day**. The fine is calculated when the return is processed and the return date is later than the due date. The administrator can view fines and mark unpaid fines as paid.

### User queries

1. A student submits a message through Contact Admin.
2. The query is saved in MySQL.
3. The administrator reviews the query and marks it resolved.

## Testing Checklist

Run these tests in the deployed application and record the actual result.

- [ ] Home page opens.
- [ ] Student registration works.
- [ ] Student login and logout work.
- [ ] Admin login and logout work.
- [ ] Admin can add, edit, and delete books.
- [ ] Student can browse and search books.
- [ ] Issuing a book updates availability correctly.
- [ ] Student return request remains pending until admin action.
- [ ] Admin approval completes the return and updates availability.
- [ ] Admin rejection leaves the issue active, if rejection is implemented in the current UI/DAO.
- [ ] Reservation can be made for an unavailable book.
- [ ] Admin can fulfill an eligible reservation after a copy becomes available.
- [ ] Fine amounts and payment statuses display correctly.
- [ ] Admin can view members.
- [ ] Student queries can be submitted and resolved.
- [ ] Admin-only pages reject non-admin users.
- [ ] CSS and UTF-8 characters display correctly.

## Troubleshooting

### HTTP 404 — Not Found

Check the URL, WAR context path, servlet mapping annotations, and whether the required servlet is deployed.

### HTTP 403 — Forbidden

Check that login creates the expected session attributes and that admin pages verify the correct role. In this project, `LoginServlet` stores `user`, `userId`, `userName`, and `userRole` in the session.

### HTTP 500 — Internal Server Error

Inspect the Tomcat console/logs for the first JSP compilation error, missing class/method, or SQL exception. Fix the first relevant cause before changing unrelated files.

### Database connection errors

Confirm MySQL is running, the database exists, credentials are correct, and the JDBC driver is available.

### Missing styles

Confirm the CSS file is included in the WAR at `css/style.css`, the JSP references the correct context-aware URL, and the latest WAR is deployed.

### Incorrect symbols

Use UTF-8 for JSP page encoding and source files. A suitable JSP directive is:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
```

Use one page directive with the desired `contentType`; avoid duplicate directives that set conflicting content types.

## Security Notes

This project is for academic demonstration. Before real-world deployment:

- Replace plain-text password handling with a secure password-hashing approach.
- Never publish database credentials or administrator passwords.
- Use prepared statements and server-side input validation.
- Enforce role-based authorization in every protected servlet.
- Invalidate sessions on logout.
- Add CSRF protection to state-changing forms.
- Use HTTPS and secure session-cookie settings outside localhost.
- Avoid exposing stack traces or sensitive server details to end users.

## Future Improvements

- Password hashing and reset flow.
- Email notifications for due dates and reservations.
- Barcode/QR-based book issue and return.
- Reports for popular books and library activity.
- Pagination and improved catalogue filtering.
- Reservation notifications and a more comprehensive queue.
- Automated integration tests.

## Author and Internship

- **Project:** Digital Library Management System
- **Internship:** OASIS INFOBYTE — Java Development Internship
- **Task:** Task 5

## License

No license is specified in the supplied project. Add a license file if you intend to distribute or reuse the project publicly.
