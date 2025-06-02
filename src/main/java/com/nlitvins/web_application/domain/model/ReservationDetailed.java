package com.nlitvins.web_application.domain.model;

import lombok.Builder;

@Builder
public class ReservationDetailed {
    private Reservation reservation;
    private Book book;
    private User user;
}
