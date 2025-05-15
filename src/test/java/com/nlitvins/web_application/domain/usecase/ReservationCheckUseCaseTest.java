package com.nlitvins.web_application.domain.usecase;


import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationCheckUseCaseTest {

    private ReservationCheckUseCase sut;

    private ReservationRepositoryFake reservationRepository;

    @BeforeAll
    void setUp() {
        reservationRepository = new ReservationRepositoryFake();
        sut = new ReservationCheckUseCase(reservationRepository);
    }

    @BeforeEach
    void clear() {
        reservationRepository.clear();
    }

    private Reservation givenReservation(int id, ReservationStatus status, short extensionCount) {
        Reservation reservation = new Reservation();
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);

        reservation.setId(id);
        reservation.setUserId(123);
        reservation.setBookId(111);
        reservation.setTermDate(dateTime.plusDays(4));
        reservation.setStatus(status);
        reservation.setExtensionCount(extensionCount);
        return reservationRepository.save(reservation);
    }

    private Reservation givenReservationWithTermDate(int id, ReservationStatus status, short extensionCount, LocalDateTime termDate) {
        Reservation reservation = new Reservation();

        reservation.setId(id);
        reservation.setUserId(123);
        reservation.setBookId(111);
        reservation.setTermDate(termDate);
        reservation.setStatus(status);
        reservation.setExtensionCount(extensionCount);
        return reservationRepository.save(reservation);
    }

    @Test
    void changeReservationStatusAndTermDateWhenReceiveBook() {
        Reservation reservation = givenReservation(1, ReservationStatus.NEW, (short) 0);

        LocalDate mockedDate = LocalDate.parse("2025-05-02");
        try (MockedStatic<LocalDate> mockedClass = mockStatic(LocalDate.class)) {
            mockedClass.when(LocalDate::now).thenReturn(mockedDate);
            Reservation result = sut.receiveBook(reservation.getId());
            assertNotNull(result);
        }

        Reservation receivedReservation = reservationRepository.findById(1);
        assertNotNull(receivedReservation);
        assertEquals(ReservationStatus.RECEIVED, receivedReservation.getStatus());
        assertEquals(LocalDateTime.parse("2025-05-16T23:59:59.999999999"), receivedReservation.getTermDate());
    }

    @Test
    void throwExceptionWhenReceiveBookWithIncorrectStatus() {
        givenReservation(1, ReservationStatus.RECEIVED, (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.receiveBook(1));
        assertEquals("You can't receive the book. Incorrect status, not new. Or reservation doesn't exist", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenReceiveBookWithNullReservation() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.receiveBook(1));
        assertEquals("You can't receive the book. Incorrect status, not new. Or reservation doesn't exist", thrown.getMessage());
    }


    @Test
    void throwExceptionWhenExtendBookWithIncorrectStatus() {
        givenReservation(1, ReservationStatus.COMPLETED, (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusNew() {
        givenReservation(1, ReservationStatus.NEW, (short) 1);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusReceived() {
        givenReservation(1, ReservationStatus.RECEIVED, (short) 3);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void extendReservationWhenStatusReceived() {
        LocalDateTime dateTime = LocalDateTime.parse("2025-05-01T12:00");
        Reservation reservation = givenReservationWithTermDate(1, ReservationStatus.RECEIVED, (short) 2, dateTime);

        Reservation result = sut.extendBook(reservation.getId());

        Reservation extendedReservation = reservationRepository.findById(1);
        assertEquals((short) 3, extendedReservation.getExtensionCount());
        assertEquals(dateTime.plusDays(14), extendedReservation.getTermDate());
    }

    @Test
    void extendReservationWhenStatusNew() {
        LocalDateTime dateTime = LocalDateTime.parse("2025-05-01T12:00");
        Reservation reservation = givenReservationWithTermDate(1, ReservationStatus.NEW, (short) 0, dateTime);

        Reservation result = sut.extendBook(reservation.getId());

        Reservation extendedReservation = reservationRepository.findById(1);
        assertEquals((short) 1, extendedReservation.getExtensionCount());
        assertEquals(dateTime.plusDays(3), extendedReservation.getTermDate());
    }

    @Test
    void extendReservationWhenEmptyReservation() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("Can't extend empty reservation", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenCompleteReservationWithIncorrectStatus() {
        givenReservation(1, ReservationStatus.LOST, (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.completeReservation(1));
        assertEquals("You can't complete reservation. Status is not received.", thrown.getMessage());
    }

    @Test
    void completeReservation() {
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, (short) 3);
        Reservation result = sut.completeReservation(reservation.getId());

        assertEquals(result.getStatus(), ReservationStatus.COMPLETED);
    }

    @Test
    void throwExceptionWhenCancelReservationWithIncorrectStatus() {
        givenReservation(1, ReservationStatus.RECEIVED, (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.cancelReservation(1));
        assertEquals("You can't cancel reservation. Status isn't new.", thrown.getMessage());
    }

    @Test
    void cancelReservation() {
        givenReservation(1, ReservationStatus.NEW, (short) 3);
        Reservation resulted = sut.cancelReservation(1);

        assertEquals(resulted.getStatus(), ReservationStatus.CANCELED);
    }

    @Test
    void loseBook() {
        givenReservation(1, ReservationStatus.RECEIVED, (short) 3);
        Reservation resulted = sut.loseBook(1);

        assertEquals(resulted.getStatus(), ReservationStatus.LOST);
    }

    @Test
    void throwExceptionWhenLostBookWithIncorrectStatus() {
        givenReservation(1, ReservationStatus.NEW, (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.loseBook(1));
        assertEquals("Book is in library, it can't be lost", thrown.getMessage());
    }
}
