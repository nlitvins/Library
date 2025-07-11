package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.User;

public interface JwtRepository {

    String getToken(User user);

    String getUserName(String token);

    String getRole(String token);

    Integer getUserId(String token);

    boolean isValidToken(String token);
}
