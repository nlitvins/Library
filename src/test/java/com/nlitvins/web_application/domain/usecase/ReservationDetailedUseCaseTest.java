package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.JwtRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationDetailedRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.UserRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationDetailedUseCaseTest {

    private ReservationDetailedUseCase sut;


    private BookRepositoryFake bookRepository;
    private ReservationRepositoryFake reservationRepository;
    private UserRepositoryFake userRepository;
    private ReservationDetailedRepositoryFake reservationDetailedRepository;
    private JwtRepository jwtRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        reservationRepository = new ReservationRepositoryFake();
        userRepository = new UserRepositoryFake();
        reservationDetailedRepository = new ReservationDetailedRepositoryFake();
        jwtRepository = new JwtRepositoryFake();
        sut = new ReservationDetailedUseCase(reservationDetailedRepository, jwtRepository);
    }

    @BeforeEach
    void clear() {
        reservationDetailedRepository.clear();
    }

    private Reservation reservationFirst(int userId) {
        return reservationRepository.save(
                Reservation.builder()
                        .id(1)
                        .userId(userId)
                        .bookId(1)
                        .createdDate(LocalDateTime.now())
                        .termDate(LocalDateTime.now().plusDays(14))
                        .status(ReservationStatus.NEW)
                        .extensionCount((short) 0)
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
    }

    private Reservation reservationSecond(int userId) {
        return reservationRepository.save(
                Reservation.builder()
                        .id(2)
                        .userId(userId)
                        .bookId(2)
                        .createdDate(LocalDateTime.now().minusDays(1))
                        .termDate(LocalDateTime.now().plusDays(13))
                        .status(ReservationStatus.NEW)
                        .extensionCount((short) 1)
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
    }

    private Book bookFirst() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("1984")
                        .author("George Orwell")
                        .isBorrowed(false)
                        .quantity(5)
                        .creationYear(LocalDate.of(1949, 6, 8))
                        .status(BookStatus.AVAILABLE)
                        .genre(BookGenre.DYSTOPIAN)
                        .pages((short) 328)
                        .edition("First Edition")
                        .releaseDate(LocalDate.of(1949, 6, 8))
                        .type(BookType.BOOK)
                        .build()
        );
    }

    private Book bookSecond() {
        return bookRepository.save(
                Book.builder()
                        .id(2)
                        .title("To Kill a Mockingbird")
                        .author("Harper Lee")
                        .isBorrowed(true)
                        .quantity(3)
                        .creationYear(LocalDate.of(1960, 7, 11))
                        .status(BookStatus.AVAILABLE)
                        .genre(BookGenre.CLASSIC)
                        .pages((short) 281)
                        .edition("Second Edition")
                        .releaseDate(LocalDate.of(1960, 7, 11))
                        .type(BookType.BOOK)
                        .build()
        );
    }

    private User user() {
        return userRepository.save(
                User.builder()
                        .id(1)
                        .name("Alice")
                        .secondName("Smith")
                        .userName("alice.smith")
                        .password("securePass123")
                        .email("alice@example.com")
                        .mobileNumber(123456789)
                        .personCode("AS123456")
                        .role(UserRole.USER)
                        .build()
        );
    }

    @Test
    void returnReservationDetailed() {
        List<ReservationDetailed> expected = reservationDetailedRepository.findAll();
        assertEquals(Collections.emptyList(), expected);

        bookFirst();
        bookSecond();
        user();
        reservationFirst(user().getId());
        reservationSecond(user().getId());

        List<ReservationDetailed> result = sut.findAll();
        assertNotNull(result);
    }

    @Test
    void returnReservationDetailedByUserId() {
        user();
        List<ReservationDetailed> expected = reservationDetailedRepository.findByUserId(user().getId());
        assertEquals(Collections.emptyList(), expected);

        bookFirst();
        bookSecond();
        reservationFirst(user().getId());
        reservationSecond(user().getId());

        List<ReservationDetailed> result = sut.findByUserId(user().getId());
        assertNotNull(result);

    }
}
