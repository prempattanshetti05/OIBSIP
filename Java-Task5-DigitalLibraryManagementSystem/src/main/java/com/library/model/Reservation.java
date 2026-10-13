
package com.library.model;

import java.sql.Timestamp;

public class Reservation {

    private int id;
    private int userId;
    private int bookId;

    private String userName;
    private String bookTitle;
    private String status;

    private Timestamp reservationDate;

    public Reservation() {
    }

    public Reservation(int id, int userId, int bookId,
            String userName, String bookTitle,
            String status, Timestamp reservationDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.status = status;
        this.reservationDate = reservationDate;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }
}