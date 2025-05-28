package com.nlitvins.web_application.domain.exception;

public class UserHasTooManyActiveReservationsException extends RuntimeException {
    public UserHasTooManyActiveReservationsException(int id) {
        super("User(id: " + id + ") has reached the limit for active reservations");
    }
}
