package com.nlitvins.web_application.inbound.rest;


import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.UserReadUseCase;
import com.nlitvins.web_application.domain.usecase.UserRegistrationUseCase;
import com.nlitvins.web_application.domain.usecase.UserSetRoleUseCase;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserReadUseCase userReadUseCase;
    private final UserRegistrationUseCase userRegistrationUseCase;
    private final UserSetRoleUseCase userSetRoleUseCase;

    public UserController(UserReadUseCase userReadUseCase, UserRegistrationUseCase userRegistrationUseCase, UserSetRoleUseCase userSetRoleUseCase) {
        this.userReadUseCase = userReadUseCase;
        this.userRegistrationUseCase = userRegistrationUseCase;
        this.userSetRoleUseCase = userSetRoleUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LIBRARIAN')")
    public List<UserResponse> getUsers() {
        // 0. validate input - throw exception
        // 1. map inbound (request) classes to domain
        // - skip - no input
        // 2. call useCase
        List<User> users = userReadUseCase.getUsers();
        // 3. map domain back to inbound (response)
        return InboundMapper.Users.toDTOList(users);
    }

    @PostMapping
    public UserResponse registerUser(@RequestBody UserRequest request) {
        User user = InboundMapper.Users.toDomain(request);
        User savedUser = userRegistrationUseCase.registerUser(user);
        return InboundMapper.Users.toDTO(savedUser);
    }

    @PutMapping("/{id}/role/user")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public UserResponse setUserRole(@PathVariable int id) {
        User user = userSetRoleUseCase.setUserRole(id);
        return InboundMapper.Users.toDTO(user);
    }

    @PutMapping("/{id}/role/librarian")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse setLibrarianRole(@PathVariable int id) {
        User user = userSetRoleUseCase.setLibrarianRole(id);
        return InboundMapper.Users.toDTO(user);
    }
}
