package com.nlitvins.web_application.inbound.model;

import lombok.Builder;

@Builder
public class UserResponse {
    private int id;
    private String name;
    private String secondName;
    private String userName;
    private String email;
    private Integer mobileNumber;
    private String personCode;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonCode() {
        return personCode;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }
}

