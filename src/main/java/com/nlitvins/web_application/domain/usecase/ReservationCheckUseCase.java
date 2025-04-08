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
}