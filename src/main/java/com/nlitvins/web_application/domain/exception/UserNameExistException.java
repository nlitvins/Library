package com.nlitvins.web_application.domain.exception;

public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String userName) {
        super("User " + userName + " already exists");
    }
}
