package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationReadUseCase;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationReadUseCaseTest {

    private ReservationReadUseCase sut;

    private ReservationRepositoryFake reservationRepository;

    @BeforeAll
    void setUp() {
        reservationRepository = new ReservationRepositoryFake();
        sut = new ReservationReadUseCase(reservationRepository, null);
    }

    @BeforeEach
    void clear() {
        reservationRepository.clear();
    }

    private Reservation newReservation(int id, int bookId, int userId) {
        return Reservation.builder()
                .id(id)
                .userId(userId)
                .bookId(bookId)
                .status(ReservationStatus.NEW)
                .build();
    }

    private Reservation givenReservation(int id, int bookId, int userId) {
        Reservation reservation = newReservation(id, bookId, userId);
        return reservationRepository.save(reservation);
    }

    @Test
    void emptyListReturnWhenGetBooksCalled() {
        List<Reservation> result = sut.getReservations();
        assertTrue(result.isEmpty());
    }

    @Test
    void returnListWhenGetReservations() {
        List<Reservation> expected = sut.getReservations();
        assertTrue(expected.isEmpty());

        Reservation reservation1 = givenReservation(1, 2, 3);
        Reservation reservation2 = givenReservation(2, 3, 4);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        List<Reservation> result = sut.getReservations();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void returnNullWhenReservationNotFound() {
        Reservation result = reservationRepository.findById(1);
        assertNull(result);
    }
}
