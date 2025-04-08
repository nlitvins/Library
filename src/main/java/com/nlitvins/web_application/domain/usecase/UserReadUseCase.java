package com.nlitvins.web_application.domain.usecase;

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
        // 1. do business
        // 2. call repository
        return userRepository.findAll();
        // 3. do business
        // 4. return domain objects
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
