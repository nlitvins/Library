package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.exception.UserLoginException;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.domain.usecase.user.UserLoginUseCase;
import com.nlitvins.web_application.outbound.repository.fake.UserRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserLoginUseCaseTest {

    private UserLoginUseCase sut;

    private JwtRepository jwtRepository;
    private UserRepositoryFake userRepository;

    @BeforeAll
    void setUp() {
        jwtRepository = mock();
        userRepository = new UserRepositoryFake();
        sut = new UserLoginUseCase(userRepository, jwtRepository);
    }

    @BeforeEach
    void clear() {
        reset(jwtRepository);
        userRepository.clear();
    }

    @Test
    void returnUserWhenUserLogin() {
        User user = givenUserFirst();

        doReturn("mocked.jwt.token").when(jwtRepository).getToken(user);

        User loginUser = User.builder()
                .userName(user.getUserName())
                .password("password123")
                .build();

        String token = sut.loginUser(loginUser);

        assertNotNull(token);
        assertEquals("mocked.jwt.token", token);
        verify(jwtRepository).getToken(user);
    }

    @Test
    void throwExceptionWhenUserIsNull() {
        User loginUser = User.builder()
                .userName("testuser3")
                .password("password123")
                .build();

        UserLoginException thrown = assertThrows(UserLoginException.class, () -> sut.loginUser(loginUser));
        assertEquals("Incorrect username or password", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenPasswordIncorrect() {
        User savedUser = givenUserFirst();
        User loginUser = User.builder()
                .userName(savedUser.getUserName())
                .password("incorrect.password")
                .build();

        UserLoginException thrown = assertThrows(UserLoginException.class, () -> sut.loginUser(loginUser));
        assertEquals("Incorrect username or password", thrown.getMessage());
    }

    private User givenUserFirst() {
        return userRepository.save(User.builder()
                .id(1)
                .name("John")
                .secondName("Doe")
                .userName("testuser3")
                .password("$2a$10$.bGhMkXjpCsjImI4ZXwYmeNvXu8qCLYErc7sjdXHBgXToo2DnSj86")
                .email("johndoe@example.com")
                .mobileNumber(20001111)
                .personCode("190201-27314")
                .role(UserRole.USER)
                .build());
    }
}
