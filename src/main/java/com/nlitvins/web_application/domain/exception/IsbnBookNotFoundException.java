package com.nlitvins.web_application.domain.exception;

public class IsbnBookNotFoundException extends RuntimeException {
    public IsbnBookNotFoundException(String isbn) {
        super("Book not found for ISBN: " + isbn);
    }
}
