package com.nlitvins.web_application.inbound.model;

import lombok.Builder;

@Builder
public class LoginResponse {
    private final String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
