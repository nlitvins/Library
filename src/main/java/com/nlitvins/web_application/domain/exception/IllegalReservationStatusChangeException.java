package com.nlitvins.web_application.domain.exception;

import com.nlitvins.web_application.domain.model.ReservationStatus;

public class IllegalReservationStatusChangeException extends RuntimeException {
    public IllegalReservationStatusChangeException(ReservationStatus status, ReservationStatus expected, int reservationId) {
        super("Reservation(id: " + reservationId + ") with status " + status + " can't be changed to " + expected);
    }
}
