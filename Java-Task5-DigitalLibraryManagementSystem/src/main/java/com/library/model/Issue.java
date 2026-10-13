package com.library.model;

import java.sql.Date;

public class Issue {

    private int id;
    private int userId;
    private int bookId;

    private String userName;
    private String userEmail;
    private String bookTitle;

    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
    private String returnStatus;

    public Issue() {
    }

    public Issue(int id, int userId, int bookId,
            String userName, String userEmail, String bookTitle,
            Date issueDate, Date dueDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnStatus() { return returnStatus; }
    public void setReturnStatus(String status) { this.returnStatus = status; }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}