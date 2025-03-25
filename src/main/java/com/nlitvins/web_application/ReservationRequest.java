package com.nlitvins.web_application;

import java.time.LocalDateTime;

public class ReservationRequest {
    private int id;
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private Short status;
    private Short extensionCount;
    private LocalDateTime updatedDate;

    public int getId() {
        return id;
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

    public Short getStatus() {
        return status;
    }

    public Short getExtensionCount() {
        return extensionCount;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }


    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setTermDate(LocalDateTime termDate) {
        this.termDate = termDate;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setExtensionCount(Short extensionCount) {
        this.extensionCount = extensionCount;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

}
