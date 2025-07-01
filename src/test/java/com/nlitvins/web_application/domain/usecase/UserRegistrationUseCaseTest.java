package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.outbound.repository.fake.UserRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegistrationUseCaseTest {

    private UserRegistrationUseCase sut;

    private UserRepositoryFake userRepository;

    @BeforeAll
    void setUp() {
        userRepository = new UserRepositoryFake();
        sut = new UserRegistrationUseCase(userRepository);
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
                .role(null)
                .build());
    }


    @Test
    void saveUserWhenCorrectUserPassed() {
        User expected = userRepository.findById(1);
        assertNull(expected);

        User user = givenUserFirst();
        User result = sut.registerUser(user);
        assertNotNull(result);

        User savedUser = userRepository.findById(1);
        assertNotNull(savedUser);
    }
}
