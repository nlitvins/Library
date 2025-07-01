package com.nlitvins.web_application.domain.usecase;


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

    private User givenUserFirst() {
        return userRepository.save(User.builder()
                .id(1)
                .name("John")
                .secondName("Doe")
                .userName("johndoe")
                .password("password123")
                .email("johndoe@example.com")
                .mobileNumber(20001111)
                .personCode("190201-27314")
                .role(UserRole.STARTER)
                .build());
    }

    @Test
    void ReturnRoleUser() {
        User user = givenUserFirst();

        User result = sut.setUserRole(user.getId());

        assertEquals(UserRole.USER, result.getRole());
    }

    @Test
    void ReturnExceptionWhenUserIsActivated() {
        User user = givenUserFirst();

        User result = sut.setUserRole(user.getId());
        assertEquals(UserRole.USER, result.getRole());

        UserAccountIsActivated thrown = assertThrows(UserAccountIsActivated.class, () -> sut.setUserRole(result.getId()));
        assertEquals("User johndoe account has been already activated", thrown.getMessage());
    }

    @Test
    void ReturnRoleLibrarian() {
        User user = givenUserFirst();

        User result = sut.setLibrarianRole(user.getId());

        assertEquals(UserRole.LIBRARIAN, result.getRole());
    }


}

