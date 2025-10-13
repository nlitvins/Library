package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class UserTestFactory {

    public static List<User> givenUsers() {
        List<User> users = new ArrayList<>();
        users.add(givenUser());
        users.add(givenUser());
        return users;
    }

    public static List<UserResponse> givenUsersResponse() {
        List<UserResponse> users = new ArrayList<>();
        users.add(givenUserResponse());
        users.add(givenUserResponse());
        return users;
    }


    public static UserRequest givenUserRequest() {
        return UserRequest.builder()
                .name("Bob")
                .secondName("Bob")
                .userName("Bob")
                .password("bob123")
                .email("bob@example.com")
                .mobileNumber(21111111)
                .personCode("120871-27314")
                .build();
    }

    public static UserResponse givenUserResponse() {
        return UserResponse.builder()
                .id(2)
                .name("Bob")
                .secondName("Bob")
                .userName("Bob")
                .email("bob@example.com")
                .mobileNumber(21111111)
                .personCode("120871-27314")
                .role(UserRole.USER)
                .build();
    }

    public static User givenUser() {
        return User.builder()
                .id(2)
                .name("Bob")
                .secondName("Bob")
                .userName("Bob")
                .password("bob123")
                .email("bob@example.com")
                .mobileNumber(21111111)
                .personCode("120871-27314")
                .role(UserRole.USER)
                .build();
    }
}