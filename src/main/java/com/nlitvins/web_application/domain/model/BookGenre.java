package com.nlitvins.web_application.domain.model;

public enum BookGenre {
    ROMANCE((short) 1),
    MODERNIST((short) 2),
    CLASSIC((short) 3),
    MAGICAL_REALISM((short) 4),
    DYSTOPIAN((short) 5),
    FANTASY((short) 6),
    ADVENTURE((short) 7),
    PHILOSOPHICAL((short) 8),
    ANCIENT((short) 9),
    RELIGIOUS((short) 10);

    public final short id;

    BookGenre(short id) {
        this.id = id;
    }

    public static BookGenre getGenre(short id) {
        for (BookGenre bookGenre : BookGenre.values()) {
            if (id == bookGenre.id) {
                return bookGenre;
            }
        }
        throw new IllegalArgumentException(String.format("Book genre with id %d does not exits", id));
    }
}
