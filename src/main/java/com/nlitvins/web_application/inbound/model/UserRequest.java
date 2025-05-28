package com.nlitvins.web_application.inbound.model;

public class UserRequest {
    private int id;
    private String name;
    private String secondName;
    private String userName;
    private String password;
    private String email;
    private Integer mobileNumber;
    private String personCode;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

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

    public String getPersonCode() {
        return personCode;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }
}

