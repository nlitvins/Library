package com.nlitvins.web_application.domain.exception;

public class UserHasSameReservationException extends RuntimeException {
    public UserHasSameReservationException(int userId, int bookId, int reservationId) {
        super("User(id: " + userId + ") has same reservation(id: " + reservationId + ") with book(id: " + bookId + ")");
    }
}
