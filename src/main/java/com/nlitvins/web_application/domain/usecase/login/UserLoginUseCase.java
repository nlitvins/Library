package com.nlitvins.web_application.domain.usecase.login;


import com.nlitvins.web_application.domain.exception.UserLoginException;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserLoginUseCase {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final JwtRepository jwtRepository;

    public UserLoginUseCase(UserRepository userRepository, JwtRepository jwtRepository) {
        this.userRepository = userRepository;
        this.jwtRepository = jwtRepository;
    }

    public String loginUser(User loginUser) {
        User userInfo = userRepository.findByUserName(loginUser.getUserName());

        if (userInfo == null || passwordNotMatches(loginUser, userInfo)) {
            throw new UserLoginException();
        }

        return jwtRepository.getToken(userInfo);
    }

    private boolean passwordNotMatches(User loginUser, User userInfo) {
        return !passwordEncoder.matches(loginUser.getPassword(), userInfo.getPassword());
    }
}
