package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationCreateUseCase {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public ReservationCreateUseCase(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }

    public Reservation registerReservation(Reservation reservation) {
        Book book = bookRepository.findById(reservation.getBookId());
        List<Short> statuses = List.of(ReservationStatus.NEW.id, ReservationStatus.RECEIVED.id, ReservationStatus.OVERDUE.id);

        if (book == null) {
            throw new IllegalArgumentException("Book doesn't exist");
        }

        if (book.getQuantity() == 0) {
            throw new RuntimeException("Quantity is zero");
        }


        List<Reservation> reservationQuantityAndStatus = reservationRepository.findByUserIdAndStatusIn(reservation.getUserId(), statuses);
        if (reservationQuantityAndStatus.size() >= 3) {
            throw new RuntimeException("Too many reservations");
        }

        Reservation reservationCheck = reservationRepository.findByBookIdAndUserId(reservation.getBookId(), reservation.getUserId());
        if (reservationCheck != null) {
            throw new RuntimeException("Reservation already exists");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        return reservationRepository.save(reservation);
    }


}
