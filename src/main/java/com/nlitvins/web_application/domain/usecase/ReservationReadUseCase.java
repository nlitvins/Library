package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class ReservationReadUseCase {

    private final ReservationRepository reservationRepository;
    private final JwtRepository jwtRepository;

    public ReservationReadUseCase(ReservationRepository reservationRepository, JwtRepository jwtRepository) {
        this.reservationRepository = reservationRepository;
        this.jwtRepository = jwtRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserId(String token) {
        Integer userId = jwtRepository.getUserId(token);
        if (userId == null) {
            return Collections.emptyList();
        }
        return reservationRepository.findByUserId(userId);
    }

    public Reservation getById(int id) {
        return reservationRepository.findById(id);
    }
}
