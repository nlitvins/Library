package com.nlitvins.web_application.inbound.rest;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.outbound.model.UserEntity;
import com.nlitvins.web_application.outbound.repository.UserRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> users() {
        List<UserEntity> userEntities = userRepository.findAll();
        return OutboundMapper.Users.toDomainList(userEntities);
    }
}
