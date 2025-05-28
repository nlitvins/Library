package com.nlitvins.web_application.domain.exception;

public class BookQuantityIsZeroException extends RuntimeException {
    public BookQuantityIsZeroException(int id) {
        super("Book: " + id + " quantity is zero");
    }
}
