package com.nlitvins.web_application.inbound.model;

import com.nlitvins.web_application.domain.model.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ReservationResponse {
    private int id;
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private LocalDateTime updatedDate;
    private ReservationStatus status;
    private short extensionCount;

    public int getId() {
        return id;
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
}