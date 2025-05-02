package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import com.nlitvins.web_application.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservationCreateUseCase {

    public final ReservationRepository reservationRepository;
    public final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReservationCreateUseCase(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Reservation registerReservation(Reservation reservation) {
//        User user = userRepository.findById(reservation.getUserId());
        Book book = bookRepository.findById(reservation.getBookId());

        reservation = reservationRepository.findByBookIdAndUserId(reservation.getBookId(), reservation.getUserId());
        if (reservation != null) {
            throw new RuntimeException("Reservation already exists");
        }

        if (book.getQuantity() == 0) {
            throw new RuntimeException("Quantity is zero");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        return reservationRepository.save(reservation);
    }


}
