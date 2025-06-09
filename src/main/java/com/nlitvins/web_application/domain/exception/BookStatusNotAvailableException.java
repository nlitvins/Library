package com.nlitvins.web_application.domain.exception;

public class BookStatusNotAvailableException extends RuntimeException {
    public BookStatusNotAvailableException() {
        super("Book isn't available");
    }
}