package com.nlitvins.web_application.domain.model;

public enum BookStatus {
    AVAILABLE((short) 1),
    NOT_AVAILABLE((short) 2);

    public final short id;

    BookStatus(short id) {
        this.id = id;
    }


    public static BookStatus getStatus(short id) {
        for (BookStatus bookStatus : BookStatus.values()) {
            if (id == bookStatus.id) {
                return bookStatus;
            }
        }
        throw new IllegalArgumentException(String.format("Book status with id %d does not exits", id));

    }
}