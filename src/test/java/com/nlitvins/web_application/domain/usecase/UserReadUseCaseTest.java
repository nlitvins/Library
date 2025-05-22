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
        return userRepository.save(new User(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", 20001111, "190201-27314", null));
    }

    private User givenUserSecond() {
        return userRepository.save(new User(2, "Jane", "Smith", "janesmith", "securePass2024", "janesmith@example.com", 20001112, "190202-27315", null));
    }

    private User givenUserWithoutId(int id) {
        return userRepository.save(new User(id, "Jane", "Smith", "janesmith", "securePass2024", "janesmith@example.com", 20001112, "190202-27315", null));
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

    @Test
    void saveUserWhenCorrectUserPassed() {
        User expected = userRepository.findById(123);
        assertNull(expected);

        int userId = 123;
        User user = givenUserWithoutId(userId);
        User result = sut.registerUser(user);
        assertNotNull(result);

        User savedUser = userRepository.findById(123);
        assertNotNull(savedUser);
    }

    @Test
    void whenFindByIdDontFindUser() {
        User result = userRepository.findById(123);
        assertNull(result);
    }
}
