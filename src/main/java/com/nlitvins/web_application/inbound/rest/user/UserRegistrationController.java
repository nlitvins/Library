package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserRegistrationUseCase;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
public class UserRegistrationController {

    private final UserRegistrationUseCase userRegistrationUseCase;

    public UserRegistrationController(UserRegistrationUseCase userRegistrationUseCase) {
        this.userRegistrationUseCase = userRegistrationUseCase;
    }

    @PostMapping
    public UserResponse registerUser(@RequestBody UserRequest request) {
        User user = InboundMapper.Users.toDomain(request);
        User savedUser = userRegistrationUseCase.registerUser(user);
        return InboundMapper.Users.toDTO(savedUser);
    }
}
