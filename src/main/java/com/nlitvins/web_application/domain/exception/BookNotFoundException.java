package com.nlitvins.web_application.domain.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int id) {
        super("Book(id: " + id + ") not found");
    }
} 