package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.outbound.repository.fake.JwtRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationDetailedRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationDetailedUseCaseTest {

    private ReservationDetailedUseCase sut;

    private ReservationDetailedRepositoryFake reservationDetailedRepository;
    private JwtRepositoryFake jwtRepository;

    @BeforeAll
    void setUp() {
        reservationDetailedRepository = new ReservationDetailedRepositoryFake();
        jwtRepository = new JwtRepositoryFake();
        sut = new ReservationDetailedUseCase(reservationDetailedRepository, jwtRepository);
    }

    @BeforeEach
    void clear() {
        reservationDetailedRepository.clear();
        jwtRepository.clear();
    }

    @Test
    void returnReservationDetailed() {
        ReservationDetailed reservationFirst = givenReservationDetailed(1, 1, 1);
        ReservationDetailed reservationSecond = givenReservationDetailed(2, 2, 2);

        List<ReservationDetailed> result = sut.getAll();
        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(List.of(reservationFirst, reservationSecond).toArray()));
    }

    @Test
    void returnReservationDetailedByUserId() {
        User user = givenUser(1);
        ReservationDetailed reservationFirst = givenReservationDetailed(1, 1, 1);
        ReservationDetailed reservationSecond = givenReservationDetailed(2, 2, 1);
        givenReservationDetailed(3, 3, 2);

        List<ReservationDetailed> result = sut.getByUserId(user.getId());
        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(List.of(reservationFirst, reservationSecond).toArray()));
    }

    @Test
    void returnReservationDetailedByToken() {
        User user = givenUser(1);
        String token = jwtRepository.save(user);
        ReservationDetailed reservationFirst = givenReservationDetailed(1, 1, 1);
        ReservationDetailed reservationSecond = givenReservationDetailed(2, 2, 1);
        givenReservationDetailed(3, 3, 2);

        List<ReservationDetailed> result = sut.getReservationsByToken(token);
        assertEquals(2, result.size());
        assertThat(result, containsInAnyOrder(List.of(reservationFirst, reservationSecond).toArray()));
    }

    @Test
    void returnEmptyListWhenUserNotFoundByToken() {
        String token = "testToken";
        List<ReservationDetailed> result = sut.getReservationsByToken(token);
        assertEquals(0, result.size());
    }

    private Reservation givenReservation(int id, int userId, int bookId) {
        return Reservation.builder()
                .id(id)
                .userId(userId)
                .bookId(bookId)
                .build();
    }

    private Book givenBook(int id) {
        return Book.builder()
                .id(id)
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.DYSTOPIAN)
                .type(BookType.BOOK)
                .build();
    }

    private User givenUser(int id) {
        return User.builder()
                .id(id)
                .role(UserRole.USER)
                .build();
    }

    private ReservationDetailed givenReservationDetailed(int reservationId, int bookId, int userId) {
        return reservationDetailedRepository.save(
                ReservationDetailed.builder()
                        .reservation(givenReservation(reservationId, userId, bookId))
                        .book(givenBook(bookId))
                        .user(givenUser(userId))
                        .build());
    }


}
