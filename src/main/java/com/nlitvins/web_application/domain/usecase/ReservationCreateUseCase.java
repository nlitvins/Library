package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.exception.BookNotFoundException;
import com.nlitvins.web_application.domain.exception.BookQuantityIsZeroException;
import com.nlitvins.web_application.domain.exception.UserHasSameReservationException;
import com.nlitvins.web_application.domain.exception.UserHasTooManyActiveReservationsException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationCreateUseCase {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public ReservationCreateUseCase(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }

    // TODO: transaction (Dima)
    public Reservation registerReservation(Reservation reservation) {
        Book book = bookRepository.findById(reservation.getBookId());

        if (book == null) {
            throw new BookNotFoundException(reservation.getBookId());
        }

        if (book.getQuantity() == 0) {
            throw new BookQuantityIsZeroException(reservation.getBookId());
        }

        List<Reservation> reservationQuantityAndStatus = reservationRepository.findByUserIdAndStatusIn(reservation.getUserId(), ReservationStatus.getNotFinalStatuses());
        if (reservationQuantityAndStatus.size() >= 3) {
            throw new UserHasTooManyActiveReservationsException(reservation.getUserId());
        }

        List<Reservation> reservationRepeat = reservationRepository.findByUserIdAndBookIdAndStatusIn(reservation.getUserId(), reservation.getBookId(), ReservationStatus.getNotFinalStatuses());
        if (!reservationRepeat.isEmpty()) {
            throw new UserHasSameReservationException(reservation.getUserId(), reservation.getBookId(), reservationRepeat.getFirst().getId());
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        reservation.setTermDate(LocalDate.now().atStartOfDay().minusNanos(1).plusDays(4));
        return reservationRepository.save(reservation);
    }


}
