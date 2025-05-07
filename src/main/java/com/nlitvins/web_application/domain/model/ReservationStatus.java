package com.nlitvins.web_application.domain.model;

import java.util.List;

public enum ReservationStatus {
    NEW((short) 1),
    RECEIVED((short) 2),
    COMPLETED((short) 3),
    CANCELED((short) 4),
    OVERDUE((short) 5),
    LOST((short) 6);

    public final short id;

    ReservationStatus(short id) {
        this.id = id;
    }

    public static List<ReservationStatus> getFinalStatuses() {
        return List.of(COMPLETED, CANCELED, LOST);
    }

    public static List<ReservationStatus> getNotFinalStatuses() {
        return List.of(NEW, RECEIVED, OVERDUE);
    }

    public static ReservationStatus getStatus(short id) {
        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (id == reservationStatus.id) {
                return reservationStatus;
            }
        }
        throw new IllegalArgumentException(String.format("Reservation status with id %d does not exits", id));
    }
}
