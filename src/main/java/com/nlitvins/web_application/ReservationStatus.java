package com.nlitvins.web_application;

public enum ReservationStatus {
    NEW(1),
    RECEIVED(2),
    COMPLETED(3),
    CANCELED(4),
    OVERDUE(5),
    LOST(6);

    private int id;

    ReservationStatus(int id) {
        this.id = id;
    }
}
