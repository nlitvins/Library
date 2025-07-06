package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.repository.JwtRepository;
import com.nlitvins.web_application.domain.repository.ReservationDetailedRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationDetailedUseCase {

    private final ReservationDetailedRepository reservationDetailedRepository;
    private final JwtRepository jwtRepository;

    public ReservationDetailedUseCase(ReservationDetailedRepository reservationDetailedRepository, JwtRepository jwtRepository) {
        this.reservationDetailedRepository = reservationDetailedRepository;
        this.jwtRepository = jwtRepository;
    }

    public List<ReservationDetailed> getAll() {
        return reservationDetailedRepository.findAll();
    }

    public List<ReservationDetailed> getReservationsByToken(String token) {
        Integer userId = jwtRepository.getUserId(token);
        if (userId == null) {
            return Collections.emptyList();
        }
        return getByUserId(userId);
    }

    public List<ReservationDetailed> getByUserId(int userId) {
        return reservationDetailedRepository.findByUserId(userId);
    }

}
