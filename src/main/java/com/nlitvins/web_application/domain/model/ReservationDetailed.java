package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(toBuilder = true)
public class ReservationDetailed {
    private Reservation reservation;
    private Book book;
    private User user;
}
