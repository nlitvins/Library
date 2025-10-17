package com.nlitvins.web_application.inbound.rest.login;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.login.UserLoginUseCase;
import com.nlitvins.web_application.inbound.model.LoginRequest;
import com.nlitvins.web_application.inbound.model.LoginResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/login", produces = APPLICATION_JSON_VALUE)
public class LoginController {

    private final UserLoginUseCase userLoginUseCase;

    public LoginController(UserLoginUseCase userLoginUseCase) {
        this.userLoginUseCase = userLoginUseCase;
    }

    @PostMapping
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        User loginUser = InboundMapper.Users.toDomain(request);
        String token = userLoginUseCase.loginUser(loginUser);
        return new LoginResponse(token);

    }
}
