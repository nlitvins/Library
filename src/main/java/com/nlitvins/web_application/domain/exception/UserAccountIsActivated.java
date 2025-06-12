package com.nlitvins.web_application.domain.exception;

import com.nlitvins.web_application.domain.model.User;

public class UserAccountIsActivated extends RuntimeException {
    public UserAccountIsActivated(User user) {
        super("User " + user.getUserName() + " account has been already activated");
    }
}
