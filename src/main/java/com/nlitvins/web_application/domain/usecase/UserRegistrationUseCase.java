package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.exception.UserLoginException;
import com.nlitvins.web_application.domain.exception.UserNameExistException;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRegistrationUseCase {


    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserRegistrationUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User user) {
        if (user == null) {
            throw new UserLoginException();
        }

        User savedUser = userRepository.findByUserName(user.getUserName());
        if (Objects.equals(user.getUserName(), savedUser.getUserName())) {
            throw new UserNameExistException(user.getUserName());
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.STARTER);
        return userRepository.save(user);
    }
}
