package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int id);

    User save(User user);
}
