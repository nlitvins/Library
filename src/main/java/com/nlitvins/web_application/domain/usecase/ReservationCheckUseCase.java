package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservationCheckUseCase {

    public final ReservationRepository reservationRepository;

    public ReservationCheckUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation receiveBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation.getStatus() != ReservationStatus.NEW.id) {
            throw new RuntimeException("You can't receive the book. Incorrect status.");
        }

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        reservation.setTermDate(dateTime.plusDays(15)); //Real term is 14 days
        reservation.setStatus(ReservationStatus.RECEIVED.id);
        reservation.setExtensionCount((short) 0);

        return reservationRepository.save(reservation);
    }

    public Reservation extendBook(int id) {
        Reservation reservation = reservationRepository.findById(id);
        LocalDateTime termDate = reservation.getTermDate();
        short extensionCount = reservation.getExtensionCount();

        if (reservation.getStatus() == ReservationStatus.NEW.id && extensionCount < 1) {
            reservation.setTermDate(termDate.plusDays(3));
        } else if (reservation.getStatus() == ReservationStatus.RECEIVED.id && extensionCount < 3) {
            reservation.setTermDate(termDate.plusDays(14));
        } else {
            throw new RuntimeException("You can't extend reservation. Incorrect status or extension count.");
        }

        LocalDateTime updatedDate = LocalDateTime.now();
        reservation.setUpdatedDate(updatedDate);
        reservation.setExtensionCount((short) (extensionCount + 1));
        return reservationRepository.save(reservation);
    }

    public Reservation completeReservation(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation.getStatus() != ReservationStatus.RECEIVED.id) {
            throw new RuntimeException("You can't complete reservation. Incorrect status.");
        }
        reservation.setStatus(ReservationStatus.COMPLETED.id);
        reservation.setUpdatedDate(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation.getStatus() != ReservationStatus.NEW.id) {
            throw new RuntimeException("You can't cancel reservation. Incorrect status.");
        }

        reservation.setStatus(ReservationStatus.CANCELED.id);
        reservation.setUpdatedDate(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    public Reservation loseBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        reservation.setStatus(ReservationStatus.LOST.id);
        reservation.setUpdatedDate(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

//    public Reservation checkQuantity(int id){
//        Reservation reservation = reservationRepository.findById(id);
//        BookEntity bookEntity = new BookEntity();
//        if(bookEntity.)
//    }

    public void checkOverdue(Short status, Short setStatus) {

    }
}