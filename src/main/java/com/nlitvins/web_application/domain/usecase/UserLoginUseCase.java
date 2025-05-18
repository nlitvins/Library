package com.nlitvins.web_application.domain.usecase;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserLoginUseCase {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;


//    public UserLoginUseCase(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//        this.userRepository = userRepository;
//    }

    public boolean login(String rawPassword, String storedHashedPassword) {
        return passwordEncoder.matches(rawPassword, storedHashedPassword);
    }

    public UserLoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(User user) {
        User localUser = userRepository.findByUserName(user.getUserName());

        if (localUser == null) {
            throw new RuntimeException("Incorrect UserName");
        }
        if (passwordEncoder.matches(user.getPassword(), localUser.getPassword())) {
            return localUser;
        } else {
            throw new RuntimeException("Incorrect password");
        }
    }
}
