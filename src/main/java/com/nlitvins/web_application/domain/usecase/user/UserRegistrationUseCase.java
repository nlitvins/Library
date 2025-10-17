package com.nlitvins.web_application.domain.usecase.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationUseCase {


    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserRegistrationUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.STARTER);
        return userRepository.save(user);
    }
}
