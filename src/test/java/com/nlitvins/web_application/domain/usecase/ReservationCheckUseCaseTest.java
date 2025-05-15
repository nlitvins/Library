package com.nlitvins.web_application.domain.usecase;


import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationCheckUseCaseTest {

    private ReservationCheckUseCase sut;

    private ReservationRepositoryFake reservationRepository;
    //TODO
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        reservationRepository = new ReservationRepositoryFake();
        sut = new ReservationCheckUseCase(reservationRepository);
    }

    @BeforeEach
    void clear() {
        bookRepository.clear();
        reservationRepository.clear();
    }

    private Reservation givenReservation(int id, ReservationStatus status, LocalDateTime dateTime, short extensionCount) {

        Reservation reservation = new Reservation();
        dateTime = LocalDate.now().atStartOfDay().minusNanos(1);

        reservation.setId(id);
        reservation.setUserId(123);
        reservation.setBookId(111);
        reservation.setCreatedDate(dateTime);
        reservation.setTermDate(dateTime.plusDays(4));
        reservation.setUpdatedDate(dateTime);
        reservation.setStatus(status);
        reservation.setExtensionCount(extensionCount);
        return reservationRepository.save(reservation);
    }

    @Test
    void changeReservationStatusAndTermDateWhenReceiveBook() {
        Reservation reservation = givenReservation(1, ReservationStatus.NEW, null, (short) 0);

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
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, dateTime.plusDays(15), (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.receiveBook(1));
        assertEquals("You can't receive the book. Incorrect status, not new.", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenExtendBookWithIncorrectStatus() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.COMPLETED, dateTime.plusDays(15), (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusNew() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.NEW, dateTime.plusDays(15), (short) 2);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusReceived() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, dateTime.plusDays(15), (short) 4);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.extendBook(1));
        assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
    }

    @Test
    void changeExtensionCountWhenExtendBook() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, dateTime.plusDays(15), (short) 2);
        Reservation resulted = sut.extendBook(reservation.getId());

        assertEquals(resulted.getExtensionCount(), (short) 3);
    }

    @Test
    void throwExceptionWhenCompleteReservationWithIncorrectStatus() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.LOST, dateTime.plusDays(15), (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.completeReservation(1));
        assertEquals("You can't complete reservation. Status is not received.", thrown.getMessage());
    }

    @Test
    void completeReservation() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, dateTime.plusDays(15), (short) 3);
        Reservation resulted = sut.completeReservation(reservation.getId());

        assertEquals(resulted.getStatus(), ReservationStatus.COMPLETED);
    }

    @Test
    void throwExceptionWhenCancelReservationWithIncorrectStatus() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, dateTime.plusDays(15), (short) 0);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.cancelReservation(1));
        assertEquals("You can't cancel reservation. Status isn't new.", thrown.getMessage());
    }

    @Test
    void cancelReservation() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.NEW, dateTime.plusDays(15), (short) 3);
        Reservation resulted = sut.cancelReservation(reservation.getId());

        assertEquals(resulted.getStatus(), ReservationStatus.CANCELED);
    }

    @Test
    void loseBook() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        Reservation reservation = givenReservation(1, ReservationStatus.NEW, dateTime.plusDays(15), (short) 3);
        Reservation resulted = sut.loseBook(reservation.getId());

        assertEquals(resulted.getStatus(), ReservationStatus.LOST);
    }
}
