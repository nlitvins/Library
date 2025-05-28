package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.exception.IllegalReservationStatusChangeException;
import com.nlitvins.web_application.domain.exception.ReservationExtensionFailedException;
import com.nlitvins.web_application.domain.exception.ReservationNotFoundException;
import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.BookRepository;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservationCheckUseCase {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;

    public ReservationCheckUseCase(ReservationRepository reservationRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
    }


    public Reservation receiveBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        if (reservation.getStatus() != ReservationStatus.NEW) {
            throw new IllegalReservationStatusChangeException(reservation.getStatus(), ReservationStatus.RECEIVED, reservation.getId());
        }

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        reservation.setTermDate(dateTime.plusDays(15)); //Real term is 14 days
        reservation.setStatus(ReservationStatus.RECEIVED);
        reservation.setExtensionCount((short) 0);

        return reservationRepository.save(reservation);
    }

    public Reservation extendBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }

        LocalDateTime termDate = reservation.getTermDate();
        short extensionCount = reservation.getExtensionCount();

        if (reservation.getStatus() == ReservationStatus.NEW && extensionCount < 1) {
            reservation.setTermDate(termDate.plusDays(3));
        } else if (reservation.getStatus() == ReservationStatus.RECEIVED && extensionCount < 3) {
            reservation.setTermDate(termDate.plusDays(14));
        } else {
            throw new ReservationExtensionFailedException();
        }

        reservation.setExtensionCount((short) (extensionCount + 1));
        return reservationRepository.save(reservation);
    }

    public Reservation completeReservation(int id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        if (reservation.getStatus() != ReservationStatus.RECEIVED && reservation.getStatus() != ReservationStatus.OVERDUE) {
            throw new IllegalReservationStatusChangeException(reservation.getStatus(), ReservationStatus.COMPLETED, reservation.getId());
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        Book book = bookRepository.findById(reservation.getBookId());
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(int id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        if (reservation.getStatus() != ReservationStatus.NEW) {
            throw new IllegalReservationStatusChangeException(reservation.getStatus(), ReservationStatus.CANCELED, reservation.getId());
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        Book book = bookRepository.findById(reservation.getBookId());
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        return reservationRepository.save(reservation);

    }

    public Reservation loseBook(int id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        if (reservation.getStatus() != ReservationStatus.RECEIVED && reservation.getStatus() != ReservationStatus.OVERDUE) {
            throw new IllegalReservationStatusChangeException(reservation.getStatus(), ReservationStatus.LOST, reservation.getId());
        }
        reservation.setStatus(ReservationStatus.LOST);


        return reservationRepository.save(reservation);
    }
}