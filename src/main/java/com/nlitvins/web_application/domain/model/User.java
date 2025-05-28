package com.nlitvins.web_application.domain.model;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder(toBuilder = true)
public class User {

    private int id;
    private String name;
    private String secondName;
    private String userName;
    private String password;
    private String email;
    private Integer mobileNumber;
    private String personCode;
    private UserRole role;

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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }

    public String getPersonCode() {
        return personCode;
    }

    public UserRole getRole() {
        return role;
    }
}
