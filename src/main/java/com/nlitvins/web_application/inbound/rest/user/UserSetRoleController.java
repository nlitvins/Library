package com.nlitvins.web_application.inbound.rest.user;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.usecase.user.UserSetRoleUseCase;
import com.nlitvins.web_application.inbound.model.UserResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
public class UserSetRoleController {

    private UserSetRoleUseCase userSetRoleUseCase;

    public UserSetRoleController(UserSetRoleUseCase userSetRoleUseCase) {
        this.userSetRoleUseCase = userSetRoleUseCase;
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
