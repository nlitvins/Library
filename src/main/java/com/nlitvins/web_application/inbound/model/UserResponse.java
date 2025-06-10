package com.nlitvins.web_application.inbound.model;

import com.nlitvins.web_application.domain.model.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private int id;
    private String name;
    private String secondName;
    private String userName;
    private String email;
    private Integer mobileNumber;
    private String personCode;
    private UserRole role;
}

