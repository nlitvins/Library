package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservationCheckUseCase {

    private final ReservationRepository reservationRepository;

    public ReservationCheckUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation receiveBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation.getStatus() != ReservationStatus.NEW) {
            throw new RuntimeException("You can't receive the book. Incorrect status, not new.");
        }

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        reservation.setTermDate(dateTime.plusDays(15)); //Real term is 14 days
        reservation.setStatus(ReservationStatus.RECEIVED);
        reservation.setExtensionCount((short) 0);

        return reservationRepository.save(reservation);
    }

    public Reservation extendBook(int id) {
        Reservation reservation = reservationRepository.findById(id);
        LocalDateTime termDate = reservation.getTermDate();
        short extensionCount = reservation.getExtensionCount();

        if (reservation.getStatus() == ReservationStatus.NEW && extensionCount < 1) {
            reservation.setTermDate(termDate.plusDays(3));
        } else if (reservation.getStatus() == ReservationStatus.RECEIVED && extensionCount < 3) {
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

        if (reservation.getStatus() != ReservationStatus.RECEIVED) {
            throw new RuntimeException("You can't complete reservation. Status is not received.");
        }
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.setUpdatedDate(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(int id) {
        Reservation reservation = reservationRepository.findById(id);

        if (reservation.getStatus() != ReservationStatus.NEW) {
            throw new RuntimeException("You can't cancel reservation. Status isn't new.");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        reservation.setUpdatedDate(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    public Reservation loseBook(int id) {
        Reservation reservation = reservationRepository.findById(id);

        reservation.setStatus(ReservationStatus.LOST);
//        reservation.setUpdatedDate(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
}