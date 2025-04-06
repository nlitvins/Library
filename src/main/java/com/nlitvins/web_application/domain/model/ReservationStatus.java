package com.nlitvins.web_application.domain.model;

public enum ReservationStatus {
    NEW((short) 1),
    RECEIVED((short) 2),
    COMPLETED((short) 3),
    CANCELED((short) 4),
    OVERDUE((short) 5),
    LOST((short) 6);

    public short id;

    ReservationStatus(short id) {
        this.id = id;
    }
}
