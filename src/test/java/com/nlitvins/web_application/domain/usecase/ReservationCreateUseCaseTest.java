package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import com.nlitvins.web_application.outbound.repository.fake.BookRepositoryFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationCreateUseCaseTest {

    private ReservationCreateUseCase sut;

    private ReservationRepository reservationRepository;
    private BookRepositoryFake bookRepository;

    @BeforeAll
    void setUp() {
        bookRepository = new BookRepositoryFake();
        reservationRepository = mock();
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

        doReturn(Collections.emptyList()).when(reservationRepository).findByUserId(userId);
        doReturn(reservation).when(reservationRepository).findByBookIdAndUserId(reservation.getBookId(), userId);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.registerReservation(reservation));
        assertEquals("Reservation already exists", thrown.getMessage());
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

}