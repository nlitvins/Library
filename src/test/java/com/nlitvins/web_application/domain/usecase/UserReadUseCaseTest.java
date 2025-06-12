package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.outbound.repository.fake.UserRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserReadUseCaseTest {

    private UserReadUseCase sut;

    private UserRepositoryFake userRepository;

    @BeforeAll
    void setUp() {
        userRepository = new UserRepositoryFake();
        sut = new UserReadUseCase(userRepository);
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

    private User givenUserSecond() {
        return userRepository.save(User.builder()
                .id(2)
                .name("Jane")
                .secondName("Smith")
                .userName("janesmith")
                .password("securePass2024")
                .email("janesmith@example.com")
                .mobileNumber(20001112)
                .personCode("190202-27315")
                .role(null)
                .build());
    }

    private User givenUserWithoutId(int id) {
        return User.builder()
                .id(id)
                .name("Jane")
                .secondName("Smith")
                .userName("janesmith")
                .password("securePass2024")
                .email("janesmith@example.com")
                .mobileNumber(20001112)
                .personCode("190202-27315")
                .role(null)
                .build();
    }

    @Test
    void EmptyListReturnWhenGetUsers() {
        List<User> result = sut.getUsers();
        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetUsersReturnList() {
        List<User> expected = sut.getUsers();
        assertTrue(expected.isEmpty());

        User user1 = givenUserFirst();
        User user2 = givenUserSecond();
        List<User> result = sut.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());
    }

//    @Test
//    void saveUserWhenCorrectUserPassed() {
//        User expected = userRepository.findById(123);
//        assertNull(expected);
//
//        int userId = 123;
//        User user = givenUserWithoutId(userId);
//        User result = sut.registerUser(user);
//        assertNotNull(result);
//
//        User savedUser = userRepository.findById(123);
//        assertNotNull(savedUser);
//    }

    @Test
    void whenFindByIdDontFindUser() {
        User result = userRepository.findById(123);
        assertNull(result);
    }
}
