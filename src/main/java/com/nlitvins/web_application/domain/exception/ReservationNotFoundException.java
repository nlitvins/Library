package com.nlitvins.web_application.domain.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(int id) {
        super("Reservation(id: " + id + ") not found");
    }
} 