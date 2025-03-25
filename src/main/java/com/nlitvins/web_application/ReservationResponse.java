package com.nlitvins.web_application;

import java.time.LocalDateTime;

public class ReservationResponse {
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private LocalDateTime updatedDate;

    public ReservationResponse(int userId, int bookId, LocalDateTime createdDate, LocalDateTime termDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.createdDate = createdDate;
        this.termDate = termDate;
    }

    public ReservationResponse(int userId, int bookId, LocalDateTime createdDate, LocalDateTime termDate, LocalDateTime updatedDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.createdDate = createdDate;
        this.termDate = termDate;
        this.updatedDate = updatedDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getTermDate() {
        return termDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setTermDate(LocalDateTime termDate) {
        this.termDate = termDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}