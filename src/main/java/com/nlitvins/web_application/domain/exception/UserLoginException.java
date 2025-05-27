package com.nlitvins.web_application.domain.exception;

public class UserLoginException extends RuntimeException {
    public UserLoginException() {
        super("Incorrect username or password");
    }
}
