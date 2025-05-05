package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import com.nlitvins.web_application.outbound.repository.fake.ReservationRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    }

    @Test
    void whenBookIsUnavailableThrowException() {
        int userId = 123;
        Book book = givenUnavailableBook();
        Reservation reservation = givenReservation(book.getId(), userId);

        bookRepository.save(book);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Quantity is zero", thrown.getMessage());
    }

    private Reservation givenReservation(int bookId, int userId) {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setUserId(userId);
        reservation.setBookId(bookId);
        return reservation;
    }

    private Book givenUnavailableBook() {
        return new Book(1233, "Lord Farquaad", "Jack", 0);
    }

    @Test
    void whenReservationAlreadyExists() {
        int userId = 123;
        int bookId = 1240;
        LocalDateTime dateTime = LocalDateTime.now();
        Book book = new Book(bookId, "Book of ra", "Karton", 3);
//        Reservation reservation = new Reservation(12, userId, 1240, dateTime, dateTime, dateTime, (short) 1, (short) 0) ;
        Reservation reservation = givenReservation(book.getId(), userId);

        bookRepository.save(book);
        reservationRepository.save(reservation);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Reservation already exists", thrown.getMessage());
    }
}