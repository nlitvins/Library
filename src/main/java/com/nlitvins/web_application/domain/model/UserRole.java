package com.nlitvins.web_application.domain.model;

public enum UserRole {
    USER((short) 1),
    LIBRARIAN((short) 2),
    ADMIN((short) 3);

    public final short id;

    UserRole(short id) {
        this.id = id;
    }

    public static UserRole getRole(short id) {
        for (UserRole userRole : UserRole.values()) {
            if (id == userRole.id) {
                return userRole;
            }
        }
        throw new IllegalArgumentException(String.format("User role with id %d does not exits", id));

    }
}
