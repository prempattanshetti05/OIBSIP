
package com.library.model;

import java.sql.Timestamp;

public class Fine {

    private int id;
    private int issueId;
    private int userId;

    private String userName;
    private String bookTitle;

    private double amount;
    private boolean paid;

    private Timestamp createdAt;

    public Fine() {
    }

    public Fine(int id, int issueId, int userId,
            String userName, String bookTitle,
            double amount, boolean paid,
            Timestamp createdAt) {
        this.id = id;
        this.issueId = issueId;
        this.userId = userId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.amount = amount;
        this.paid = paid;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
