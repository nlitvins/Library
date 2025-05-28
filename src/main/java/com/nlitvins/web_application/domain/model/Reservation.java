package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder(toBuilder = true)
public class Reservation {

    private int id;
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private ReservationStatus status;
    private short extensionCount;
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

    public ReservationStatus getStatus() {
        return status;
    }

    public Short getExtensionCount() {
        return extensionCount;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

}
