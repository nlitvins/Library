package com.nlitvins.web_application.domain.model;

public enum BookType {
    BOOK((short) 1),
    MAGAZINE((short) 2),
    NEWSPAPER((short) 3);

    public final short id;

    BookType(short id) {
        this.id = id;
    }


    public static BookType getType(short id) {
        for (BookType bookType : BookType.values()) {
            if (id == bookType.id) {
                return bookType;
            }
        }
        throw new IllegalArgumentException(String.format("Book genre with id %d does not exits", id));
    }
}
