package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.inbound.model.LoginRequest;
import com.nlitvins.web_application.inbound.model.LoginResponse;

public class LoginTestFactory {

    public static User loginUser() {
        return User.builder()
                .userName("testName")
                .password("testPassword")
                .build();
    }

    public static LoginRequest loginRequest() {
        return LoginRequest.builder()
                .username("testName")
                .password("testPassword")
                .build();
    }

    public static LoginResponse loginResponse() {
        return LoginResponse.builder()
                .token("testToken")
                .build();
    }
}
