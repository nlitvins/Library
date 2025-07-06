package com.nlitvins.web_application.domain.exception;

public class ReservationExtendionFailedException extends RuntimeException {
    public ReservationExtendionFailedException() {
        super("You can't extend reservation. Incorrect status or extension count.");
    }
}
