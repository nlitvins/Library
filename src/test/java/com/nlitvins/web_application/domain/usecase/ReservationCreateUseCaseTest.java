package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO: book qu antity test
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
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setUserId(userId);
        reservation.setBookId(bookId);
        reservation.setStatus(ReservationStatus.NEW);
        return reservation;
    }

    private Book givenUnavailableBook() {
        return bookRepository.save(new Book(1233, "Lord Farquaad", "Jack", 0));
    }

    private Book givenAvailableBook(int id) {
        return bookRepository.save(new Book(id, "Book of rust", "Karton", 3));
    }

    @Test
    void throwExceptionWhenBookIsUnavailable() {
        int userId = 123;
        Book book = givenUnavailableBook();
        Reservation reservation = newReservation(1, book.getId(), userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Quantity is zero", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenReservationAlreadyExists() {
        int userId = 123;
        Book book = givenAvailableBook(123);
        Reservation reservation = givenReservation(1, book.getId(), userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Reservation already exists", thrown.getMessage());
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

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Too many reservations", thrown.getMessage());
    }

    @Test
    void throwExceptionWhenBookDoesntExist() {
        int userId = 123;
        Reservation reservation = givenReservation(121, 122, userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Book doesn't exist", thrown.getMessage());
    }

    @Test
    void saveReservationWhenReservationAdded() {
        Reservation expected = reservationRepository.findById(121);
        assertNull(expected);

        int userId = 123;
        Book book = givenAvailableBook(111);
        Reservation reservation = newReservation(121, book.getId(), userId);
        Reservation result = sut.registerReservation(reservation);
        assertNotNull(result);

        Reservation savedReservation = reservationRepository.findById(121);
        assertNotNull(savedReservation);
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