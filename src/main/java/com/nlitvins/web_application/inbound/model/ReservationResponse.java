package com.nlitvins.web_application.inbound.model;

import com.nlitvins.web_application.domain.model.ReservationStatus;

import java.time.LocalDateTime;

public class ReservationResponse {
    private int id;
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private LocalDateTime updatedDate;
    private ReservationStatus status;
    private short extensionCount;

    public ReservationResponse(
            int id,
            int userId,
            int bookId,
            LocalDateTime createdDate,
            LocalDateTime termDate,
            short extensionCount
    ) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.createdDate = createdDate;
        this.termDate = termDate;
        this.extensionCount = extensionCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setExtensionCount(short extensionCount) {
        this.extensionCount = extensionCount;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public short getExtensionCount() {
        return extensionCount;
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