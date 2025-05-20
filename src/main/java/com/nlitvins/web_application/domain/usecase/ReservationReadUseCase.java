package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReservationReadUseCase {

    private final ReservationRepository reservationRepository;

    public ReservationReadUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation getById(int id) {
        return reservationRepository.findById(id);
    }
}
