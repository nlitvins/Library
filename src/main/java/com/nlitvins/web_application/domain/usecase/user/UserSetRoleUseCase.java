package com.nlitvins.web_application.domain.usecase.user;

import com.nlitvins.web_application.domain.exception.UserAccountIsActivated;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserSetRoleUseCase {

    private final UserRepository userRepository;

    public UserSetRoleUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User setUserRole(int id) {
        return setRole(id, UserRole.USER);
    }

    public User setLibrarianRole(int id) {
        return setRole(id, UserRole.LIBRARIAN);
    }

    private User setRole(int id, UserRole role) {
        User user = userRepository.findById(id);
        if (user.getRole() != UserRole.STARTER) {
            throw new UserAccountIsActivated(user);
        }
        user.setRole(role);
        return userRepository.save(user);
    }
}
