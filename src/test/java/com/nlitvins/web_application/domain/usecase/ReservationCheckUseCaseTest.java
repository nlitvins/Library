package com.nlitvins.web_application.domain.usecase;


import com.nlitvins.web_application.domain.exception.IllegalReservationStatusChangeException;
import com.nlitvins.web_application.domain.exception.ReservationExtendionFailedException;
import com.nlitvins.web_application.domain.exception.ReservationNotFoundException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationCheckUseCaseTest {

    private ReservationCheckUseCase sut;

    private ReservationRepositoryFake reservationRepository;
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        reservationRepository = new ReservationRepositoryFake();
        sut = new ReservationCheckUseCase(reservationRepository, bookRepository);
    }

    @BeforeEach
    void clear() {
        bookRepository.clear();
        reservationRepository.clear();
    }

    private Reservation givenReservation(int id, ReservationStatus status, short extensionCount) {
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);

        return reservationRepository.save(Reservation.builder()
                .id(id)
                .userId(123)
                .bookId(111)
                .termDate(dateTime.plusDays(4))
                .status(status)
                .extensionCount(extensionCount)
                .build());
    }

    private Book givenBook() {
        return bookRepository.save(
                Book.builder()
                        .id(1)
                        .title("Fiona")
                        .author("Jack")
                        .quantity(1)
                        .build()
        );
    }

    private Reservation givenReservationWithTermDate(int id, ReservationStatus status, short extensionCount, LocalDateTime termDate) {
        return reservationRepository.save(Reservation.builder()
                .id(id)
                .userId(123)
                .bookId(111)
                .termDate(termDate)
                .status(status)
                .extensionCount(extensionCount)
                .build());
    }

    private Reservation givenReservationWithoutBookId(int id, int bookId, ReservationStatus status) {
        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        return reservationRepository.save(Reservation.builder()
                .id(id)
                .userId(123)
                .bookId(bookId)
                .termDate(dateTime)
                .status(status)
                .extensionCount((short) 0)
                .build());
    }

    @Nested
    class Receive {
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
            Reservation reservation = givenReservation(1, ReservationStatus.RECEIVED, (short) 0);

            IllegalReservationStatusChangeException thrown = assertThrows(IllegalReservationStatusChangeException.class, () -> sut.receiveBook(1));
            assertEquals("Reservation(id: " + reservation.getId() + ") with status RECEIVED can't be changed to RECEIVED", thrown.getMessage());
        }

        @Test
        void throwExceptionWhenReceiveBookWithNullReservation() {
            ReservationNotFoundException thrown = assertThrows(ReservationNotFoundException.class, () -> sut.receiveBook(1));
            assertEquals("Reservation(id: 1) not found", thrown.getMessage());
        }
    }

    @Nested
    class Extend {
        @MethodSource("nonExtendableStatuses")
        @ParameterizedTest
        void throwExceptionWhenExtendBookWithIncorrectStatus(ReservationStatus status) {
            givenReservation(1, status, (short) 0);

            ReservationExtendionFailedException thrown = assertThrows(ReservationExtendionFailedException.class, () -> sut.extendBook(1));
            assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
        }

        @MethodSource("newStatus")
        @ParameterizedTest
        void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusNew(ReservationStatus status) {
            givenReservation(1, status, (short) 1);

            ReservationExtendionFailedException thrown = assertThrows(ReservationExtendionFailedException.class, () -> sut.extendBook(1));
            assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
        }

        @MethodSource("extendableStatuses")
        @ParameterizedTest
        void throwExceptionWhenExtendBookWithIncorrectExtensionCountAndStatusReceived(ReservationStatus status) {
            givenReservation(1, status, (short) 3);

            ReservationExtendionFailedException thrown = assertThrows(ReservationExtendionFailedException.class, () -> sut.extendBook(1));
            assertEquals("You can't extend reservation. Incorrect status or extension count.", thrown.getMessage());
        }

        static Stream<ReservationStatus> newStatus() {
            return Stream.of(ReservationStatus.NEW);
        }

        static Stream<ReservationStatus> extendableStatuses() {
            return Stream.of(
                    ReservationStatus.RECEIVED,
                    ReservationStatus.NEW
            );
        }

        static Stream<ReservationStatus> nonExtendableStatuses() {
            return Stream.of(
                    ReservationStatus.OVERDUE,
                    ReservationStatus.CANCELED,
                    ReservationStatus.COMPLETED,
                    ReservationStatus.LOST
            );
        }
    }

    @Nested
    class ExtendDate {
        @Test
        void extendReservationWhenStatusReceived() {
            LocalDateTime dateTime = LocalDateTime.parse("2025-05-01T12:00");
            Reservation reservation = givenReservationWithTermDate(1, ReservationStatus.RECEIVED, (short) 2, dateTime);

            sut.extendBook(reservation.getId());

            Reservation extendedReservation = reservationRepository.findById(1);
            assertEquals((short) 3, extendedReservation.getExtensionCount());
            assertEquals(dateTime.plusDays(14), extendedReservation.getTermDate());
        }

        @Test
        void extendReservationWhenStatusNew() {
            LocalDateTime dateTime = LocalDateTime.parse("2025-05-01T12:00");
            Reservation reservation = givenReservationWithTermDate(1, ReservationStatus.NEW, (short) 0, dateTime);

            sut.extendBook(reservation.getId());

            Reservation extendedReservation = reservationRepository.findById(1);
            assertEquals((short) 1, extendedReservation.getExtensionCount());
            assertEquals(dateTime.plusDays(3), extendedReservation.getTermDate());
        }

        @Test
        void extendReservationWhenEmptyReservation() {
            ReservationNotFoundException thrown = assertThrows(ReservationNotFoundException.class, () -> sut.extendBook(1));
            assertEquals("Reservation(id: 1) not found", thrown.getMessage());
        }
    }

    @Nested
    class Complete {
        @ParameterizedTest()
        @MethodSource("completableStatuses")
        void completeReservation(ReservationStatus status) {
            Book book = givenBook();
            Reservation reservation = givenReservationWithoutBookId(1, book.getId(), status);
            Reservation result = sut.completeReservation(reservation.getId());
            Book bookResult = bookRepository.findById(result.getBookId());

            assertEquals(ReservationStatus.COMPLETED, result.getStatus());
            assertEquals((short) 2, bookResult.getQuantity());
        }

        @ParameterizedTest
        @MethodSource("nonCompletableStatuses")
        void throwExceptionWhenCompleteReservationWithIncorrectStatus(ReservationStatus status) {
            Reservation reservation = givenReservation(1, status, (short) 3);
            int reservationId = reservation.getId();
            IllegalReservationStatusChangeException thrown = assertThrows(IllegalReservationStatusChangeException.class, () -> sut.completeReservation(reservationId));
            assertEquals("Reservation(id: " + reservationId + ") with status " + status + " can't be changed to COMPLETED", thrown.getMessage());
        }


        static Stream<ReservationStatus> completableStatuses() {
            return Stream.of(
                    ReservationStatus.RECEIVED,
                    ReservationStatus.OVERDUE
            );
        }

        static Stream<ReservationStatus> nonCompletableStatuses() {
            return Stream.of(
                    ReservationStatus.NEW,
                    ReservationStatus.CANCELED,
                    ReservationStatus.COMPLETED,
                    ReservationStatus.LOST
            );
        }
    }

    @Nested
    class Cancel {
        @ParameterizedTest()
        @MethodSource("nonCancelableStatuses")
        void throwExceptionWhenCancelReservationWithIncorrectStatus(ReservationStatus status) {
            Reservation reservation = givenReservation(1, status, (short) 3);

            IllegalReservationStatusChangeException thrown = assertThrows(IllegalReservationStatusChangeException.class, () -> sut.cancelReservation(1));
            assertEquals("Reservation(id: " + reservation.getId() + ") with status " + status + " can't be changed to CANCELED", thrown.getMessage());
        }

        @ParameterizedTest()
        @MethodSource("cancelableStatuses")
        void cancelReservationWithStatusNew(ReservationStatus status) {
            Book book = givenBook();
            givenReservationWithoutBookId(1, book.getId(), status);

            Reservation result = sut.cancelReservation(1);
            Book bookResult = bookRepository.findById(result.getBookId());
            assertEquals(ReservationStatus.CANCELED, result.getStatus());

            assertEquals((short) 2, bookResult.getQuantity());
        }

        static Stream<ReservationStatus> cancelableStatuses() {
            return Stream.of(
                    ReservationStatus.NEW
            );
        }

        static Stream<ReservationStatus> nonCancelableStatuses() {
            return Stream.of(
                    ReservationStatus.RECEIVED,
                    ReservationStatus.CANCELED,
                    ReservationStatus.COMPLETED,
                    ReservationStatus.LOST,
                    ReservationStatus.OVERDUE
            );
        }
    }

    @Nested
    class Lost {
        @MethodSource("lostableStatuses")
        @ParameterizedTest
        void loseBook(ReservationStatus status) {
            givenReservation(1, status, (short) 3);
            Reservation resulted = sut.loseBook(1);

            assertEquals(ReservationStatus.LOST, resulted.getStatus());
        }

        @MethodSource("nonLostableStatuses")
        @ParameterizedTest
        void throwExceptionWhenLostBookWithIncorrectStatus(ReservationStatus status) {
            givenReservation(1, status, (short) 0);

            IllegalReservationStatusChangeException thrown = assertThrows(IllegalReservationStatusChangeException.class, () -> sut.loseBook(1));
            assertEquals("Reservation(id: 1) with status " + status + " can't be changed to LOST", thrown.getMessage());
        }

        static Stream<ReservationStatus> lostableStatuses() {
            return Stream.of(
                    ReservationStatus.RECEIVED,
                    ReservationStatus.OVERDUE
            );
        }

        static Stream<ReservationStatus> nonLostableStatuses() {
            return Stream.of(
                    ReservationStatus.NEW,
                    ReservationStatus.LOST,
                    ReservationStatus.CANCELED,
                    ReservationStatus.COMPLETED
            );
        }

    }
}
