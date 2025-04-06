package com.nlitvins.web_application.inbound.job;

import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationTermCheckJob {

    private final ReservationRepository reservationRepository;

    public ReservationTermCheckJob(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    //    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "*/5 * * * * *")
    public void newTermDateCheck() {
        List<Short> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.NEW.id);

        LocalDateTime termDate = LocalDateTime.now();

        List<ReservationEntity> reservations = reservationRepository.findByStatusInAndTermDateBefore(statusList, termDate);
        if (reservations.isEmpty()) {
            return;
        }

        for (int index = reservations.size() - 1; index >= 0; index--) {
            ReservationEntity reservationEntity = reservations.get(index);
            reservationEntity.setStatus(ReservationStatus.CANCELED.id);
        }

        reservationRepository.saveAll(reservations);
    }


    @Scheduled(cron = "*/5 * * * * *")
    public void receivedTermDateCheck() {
        List<Short> statusList = new ArrayList<>();
        statusList.add(ReservationStatus.RECEIVED.id);

        LocalDateTime termDate = LocalDateTime.now();

        List<ReservationEntity> reservations = reservationRepository.findByStatusInAndTermDateBefore(statusList, termDate);
        if (reservations.isEmpty()) {
            return;
        }

        for (int index = reservations.size() - 1; index >= 0; index--) {
            ReservationEntity reservationEntity = reservations.get(index);
            reservationEntity.setStatus(ReservationStatus.OVERDUE.id);
        }

        reservationRepository.saveAll(reservations);
    }
}
