package com.nlitvins.web_application.domain.usecase.user;


import com.nlitvins.web_application.domain.exception.UserAccountIsActivated;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.outbound.repository.fake.UserRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserSetRoleUseCaseTest {

    private UserSetRoleUseCase sut;

    private UserRepositoryFake userRepository;

    @BeforeAll
    void setUp() {
        userRepository = new UserRepositoryFake();
        sut = new UserSetRoleUseCase(userRepository);
    }

    @BeforeEach
    void clear() {
        userRepository.clear();
    }

    @Test
    void returnRoleUserWhenUserRoleStarter() {
        User user = givenUserFirst(UserRole.STARTER);

        User result = sut.setUserRole(user.getId());

        assertEquals(UserRole.USER, result.getRole());
    }

    @Test
    void returnExceptionWhenUserIsActivated() {
        User user = givenUserFirst(UserRole.USER);
        int userId = user.getId();

        UserAccountIsActivated thrown = assertThrows(UserAccountIsActivated.class, () -> sut.setUserRole(userId));
        assertEquals("User johndoe account has been already activated", thrown.getMessage());
    }

    @Test
    void returnRoleLibrarianWhenUserRoleStarter() {
        User user = givenUserFirst(UserRole.STARTER);

        User result = sut.setLibrarianRole(user.getId());

        assertEquals(UserRole.LIBRARIAN, result.getRole());
    }

    @Test
    void returnExceptionWhenLibrarianIsActivated() {
        User user = givenUserFirst(UserRole.LIBRARIAN);
        int userId = user.getId();

        UserAccountIsActivated thrown = assertThrows(UserAccountIsActivated.class, () -> sut.setLibrarianRole(userId));
        assertEquals("User johndoe account has been already activated", thrown.getMessage());
    }

    private User givenUserFirst(UserRole role) {
        return userRepository.save(User.builder()
                .id(1)
                .name("John")
                .secondName("Doe")
                .userName("johndoe")
                .password("password123")
                .email("johndoe@example.com")
                .mobileNumber(20001111)
                .personCode("190201-27314")
                .role(role)
                .build());
    }
}

