package com.nlitvins.web_application.inbound.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    private String name;
    private String secondName;
    private String userName;
    private String password;
    private String email;
    private Integer mobileNumber;
    private String personCode;
}

