package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserReadUseCase {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserReadUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getUsers() {
        // 1. do business
        // 2. call repository
        return userRepository.findAll();
        // 3. do business
        // 4. return domain objects
    }

    public User registerUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }
}
