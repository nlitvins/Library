package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
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
}
