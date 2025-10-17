package com.nlitvins.web_application.inbound.model;

import com.nlitvins.web_application.domain.model.ReservationStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class ReservationResponse {
    private int id;
    private int userId;
    private int bookId;
    private LocalDateTime createdDate;
    private LocalDateTime termDate;
    private LocalDateTime updatedDate;
    private ReservationStatus status;
    private short extensionCount;
}