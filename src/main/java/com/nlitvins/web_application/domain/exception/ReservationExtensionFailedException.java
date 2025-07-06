package com.nlitvins.web_application.domain.exception;

public class ReservationExtensionFailedException extends RuntimeException {
    public ReservationExtensionFailedException() {
        super("You can't extend reservation. Incorrect status or extension count.");
    }
}
