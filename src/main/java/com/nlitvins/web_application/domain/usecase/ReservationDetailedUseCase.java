package com.nlitvins.web_application.domain.usecase;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.repository.ReservationDetailedRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationDetailedUseCase {

    private final ReservationDetailedRepository reservationDetailedRepository;

    public ReservationDetailedUseCase(ReservationDetailedRepository reservationDetailedRepository) {
        this.reservationDetailedRepository = reservationDetailedRepository;
    }

    public List<ReservationDetailed> findAll() {
        return reservationDetailedRepository.findAll();
    }

    public List<ReservationDetailed> findByUserId(int userId) {
        return reservationDetailedRepository.findByUserId(userId);
    }

}
