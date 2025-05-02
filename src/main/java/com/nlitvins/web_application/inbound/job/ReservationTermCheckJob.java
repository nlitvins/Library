package com.nlitvins.web_application.inbound.job;

import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.repository.jpa.ReservationJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationTermCheckJob {

    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationTermCheckJob(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }


    //    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "*/5 * * * * *")
    public void checkTermDateForNewReservations() {
        Short status = ReservationStatus.NEW.id;
        Short setStatus = ReservationStatus.CANCELED.id;
        changeStatusForOverdueReservation(status, setStatus);
    }

    //    @Scheduled(cron = "*/5 * * * * *")
    public void checkTermDateForReceivedReservations() {
        Short status = ReservationStatus.RECEIVED.id;
        Short setStatus = ReservationStatus.OVERDUE.id;
        changeStatusForOverdueReservation(status, setStatus);
    }

    private void changeStatusForOverdueReservation(Short status, Short setStatus) {
        LocalDateTime now = LocalDateTime.now();

        List<ReservationEntity> reservations = reservationJpaRepository.findByStatusAndTermDateBefore(status, now);
        if (reservations.isEmpty()) {
            return;
        }
        for (int index = 0; index < reservations.size(); index++) {
            ReservationEntity reservationEntity = reservations.get(index);
            reservationEntity.setStatus(setStatus);
            reservationEntity.setUpdatedDate(now);
        }
        reservationJpaRepository.saveAll(reservations);
    }
}
