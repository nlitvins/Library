package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserReadUseCase;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
public class UserReadController {

    private final UserReadUseCase userReadUseCase;

    public UserReadController(UserReadUseCase userReadUseCase) {
        this.userReadUseCase = userReadUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIBRARIAN')")
    public List<UserResponse> getUsers() {
        List<User> users = userReadUseCase.getUsers();
        return InboundMapper.Users.toDTOList(users);
    }
}
