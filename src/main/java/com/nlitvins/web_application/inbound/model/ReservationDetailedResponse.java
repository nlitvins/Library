package com.nlitvins.web_application.inbound.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReservationDetailedResponse {
    private ReservationResponse reservation;
    private BookResponse book;
    private UserResponse user;
}
