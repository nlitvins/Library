package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.exception.BookNotFoundException;
import com.nlitvins.web_application.domain.exception.BookQuantityIsZeroException;
import com.nlitvins.web_application.domain.exception.BookStatusNotAvailableException;
import com.nlitvins.web_application.domain.exception.UserHasSameReservationException;
import com.nlitvins.web_application.domain.exception.UserHasTooManyActiveReservationsException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookStatus;
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
class ReservationCreateUseCaseTest {

    private ReservationCreateUseCase sut;

    private ReservationRepositoryFake reservationRepository;
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        reservationRepository = new ReservationRepositoryFake();
        sut = new ReservationCreateUseCase(reservationRepository, bookRepository);
    }

    @BeforeEach
    void clear() {
        bookRepository.clear();
        reservationRepository.clear();
    }

    private Reservation givenReservation(int id, int bookId, int userId) {
        Reservation reservation = newReservation(id, bookId, userId);
        return reservationRepository.save(reservation);
    }

    private Reservation givenReservationCompleted(int id, int bookId, int userId) {
        Reservation reservation = newReservation(id, bookId, userId);
        reservation.setStatus(ReservationStatus.COMPLETED);
        return reservationRepository.save(reservation);
    }

    private Reservation newReservation(int id, int bookId, int userId) {
        return Reservation.builder()
                .id(id)
                .userId(userId)
                .bookId(bookId)
                .status(ReservationStatus.NEW)
                .build();
    }

    private Book givenBookWithQuantityZero() {
        return bookRepository.save(
                Book.builder()
                        .id(1233)
                        .title("Lord Farquaad")
                        .author("Jack")
                        .quantity(0)
                        .status(BookStatus.AVAILABLE)
                        .build()
        );
    }

    private Book givenAvailableBook(int id) {
        return bookRepository.save(
                Book.builder()
                        .id(id)
                        .title("Book of rust")
                        .author("Karton")
                        .quantity(3)
                        .status(BookStatus.AVAILABLE)
                        .build()
        );
    }

    private Book givenBookWithStatusNotAvailable(int id) {
        return bookRepository.save(
                Book.builder()
                        .id(id)
                        .title("Book of rust")
                        .author("Karton")
                        .quantity(3)
                        .status(BookStatus.NOT_AVAILABLE)
                        .build()
        );
    }

    @Test
    void throwExceptionWhenBookIsUnavailable() {
        int userId = 123;
        Book book = givenBookWithQuantityZero();
        Reservation reservation = newReservation(1, book.getId(), userId);

        BookQuantityIsZeroException thrown = assertThrows(BookQuantityIsZeroException.class, () -> sut.registerReservation(reservation));
        assertEquals("Book: " + book.getId() + " quantity is zero", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenBookStatusIsNotAvailable() {
        int userId = 123;
        Book book = givenBookWithStatusNotAvailable(userId);
        Reservation reservation = newReservation(1, book.getId(), userId);

        BookStatusNotAvailableException thrown = assertThrows(BookStatusNotAvailableException.class, () -> sut.registerReservation(reservation));
        assertEquals("Book isn't available", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenReservationAlreadyExists() {
        int userId = 123;
        Book book = givenAvailableBook(111);
        Reservation reservation = givenReservation(1, book.getId(), userId);

        UserHasSameReservationException thrown = assertThrows(UserHasSameReservationException.class, () -> sut.registerReservation(reservation));
        assertEquals(("User(id: " + userId + ") has same reservation(id: " + reservation.getId() + ") with book(id: " + book.getId() + ")"), thrown.getMessage());
    }

    @Test
    void throwExceptionWhenTooMuchReservations() {
        int userId = 123;
        Book firstBook = givenAvailableBook(111);
        Book secondBook = givenAvailableBook(112);
        Book thirdBook = givenAvailableBook(113);
        Book fourthBook = givenAvailableBook(114);
        givenReservation(123, firstBook.getId(), userId);
        givenReservation(124, secondBook.getId(), userId);
        givenReservation(125, thirdBook.getId(), userId);
        Reservation reservation = givenReservation(126, fourthBook.getId(), userId);

        UserHasTooManyActiveReservationsException thrown = assertThrows(UserHasTooManyActiveReservationsException.class, () -> sut.registerReservation(reservation));
        assertEquals("User(id: " + userId + ") has reached the limit for active reservations", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenBookDoesntExist() {
        int userId = 123;
        Reservation reservation = givenReservation(121, 122, userId);

        BookNotFoundException thrown = assertThrows(BookNotFoundException.class, () -> sut.registerReservation(reservation));
        assertEquals("Book(id: " + reservation.getBookId() + ") not found", thrown.getMessage());
    }

    @Test
    void saveReservationWhenReservationAdded() {
        Reservation expected = reservationRepository.findById(121);
        assertNull(expected);

        int userId = 123;
        Book book = givenAvailableBook(111);
        Reservation reservation = newReservation(121, book.getId(), userId);

        sut.registerReservation(reservation);

        Reservation savedReservation = reservationRepository.findById(121);
        assertNotNull(savedReservation);
    }

    @Test
    void savaReservationWhenBookStatusAvailable() {
        Reservation expected = reservationRepository.findById(121);
        assertNull(expected);

        int userId = 123;
        Book book = givenAvailableBook(userId);
        Reservation reservation = newReservation(1, book.getId(), userId);

        sut.registerReservation(reservation);
        Reservation savedReservation = reservationRepository.findById(1);
        assertNotNull(savedReservation);
    }

    @Test
    void calculateReservationTermDateWhenReservationAdded() {
        Book book = givenAvailableBook(111);
        Reservation reservation = newReservation(121, book.getId(), 123);

        LocalDate mockedDate = LocalDate.parse("2025-05-02");
        try (MockedStatic<LocalDate> mockedClass = mockStatic(LocalDate.class)) {
            mockedClass.when(LocalDate::now).thenReturn(mockedDate);
            sut.registerReservation(reservation);
        }

        Reservation savedReservation = reservationRepository.findById(121);
        assertEquals(LocalDateTime.parse("2025-05-05T23:59:59.999999999"), savedReservation.getTermDate());
    }

    @Test
    void bookQuantityReducedWhenBookSave() {
        int userId = 123;
        givenAvailableBook(111);
        Reservation reservation = newReservation(121, 111, userId);

        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Book book = bookRepository.findById(111);
        assertEquals(2, book.getQuantity());
    }

    @Test
    void saveReservationWhenSecondReservationAdded() {
        int userId = 123;
        Book book = givenAvailableBook(111);
        Book book1 = givenAvailableBook(112);
        givenReservation(121, book.getId(), userId);
        Reservation reservation = newReservation(122, book1.getId(), userId);

        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Reservation savedReservation = reservationRepository.findById(122);
        assertNotNull(savedReservation);
    }

    @Test
    void saveReservationWhenThreeCompletedReservationExist() {
        int userId = 123;
        Book book = givenAvailableBook(111);
        Book book1 = givenAvailableBook(112);
        Book book2 = givenAvailableBook(113);
        Book book3 = givenAvailableBook(114);
        givenReservationCompleted(121, book.getId(), userId);
        givenReservationCompleted(122, book1.getId(), userId);
        givenReservationCompleted(123, book2.getId(), userId);

        Reservation reservation = newReservation(124, book3.getId(), userId);

        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Reservation savedReservation = reservationRepository.findById(124);
        assertNotNull(savedReservation);
    }

    @Test
    void saveReservationWhenSameReservationCompleted() {
        int userId = 123;
        Book book = givenAvailableBook(111);
        givenReservationCompleted(121, book.getId(), userId);

        Reservation reservation = newReservation(122, book.getId(), userId);

        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Reservation savedReservation = reservationRepository.findById(122);
        assertNotNull(savedReservation);
    }

    @Test
    void saveReservation() {
        Reservation expected = reservationRepository.findById(1);
        assertNull(expected);

        int userId = 123;
        Book book = givenAvailableBook(111);

        Reservation reservation = newReservation(1, book.getId(), userId);
        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Reservation savedReservation = reservationRepository.findById(1);
        assertNotNull(savedReservation);
    }
}