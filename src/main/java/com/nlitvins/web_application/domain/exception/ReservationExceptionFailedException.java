package com.nlitvins.web_application.domain.exception;

public class ReservationExceptionFailedException extends RuntimeException {
    public ReservationExceptionFailedException() {
        super("You can't extend reservation. Incorrect status or extension count.");
    }
}
