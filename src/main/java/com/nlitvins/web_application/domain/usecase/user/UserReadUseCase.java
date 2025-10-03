package com.nlitvins.web_application.domain.usecase.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserReadUseCase {

    private final UserRepository userRepository;

    public UserReadUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
