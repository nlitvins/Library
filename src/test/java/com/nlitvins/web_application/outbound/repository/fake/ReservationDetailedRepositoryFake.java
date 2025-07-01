package com.nlitvins.web_application.outbound.repository.fake;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.repository.ReservationDetailedRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ReservationDetailedRepositoryFake implements ReservationDetailedRepository {

    private final HashMap<Integer, ReservationDetailed> reservationDetaileds = new HashMap<>();

    @Override
    public List<ReservationDetailed> findAll() {
        return reservationDetaileds.values().stream()
                .map(ReservationDetailedRepositoryFake::getCopy)
                .toList();
    }

    @Override
    public List<ReservationDetailed> findByUserId(int userId) {
        List<ReservationDetailed> result = new ArrayList<>();
        for (ReservationDetailed reservationDetailed : reservationDetaileds.values()) {
            if (reservationDetailed.getUser().getId() == userId) {
                result.add(getCopy(reservationDetailed));
            }
        }
        return result;
    }

    public ReservationDetailed save(ReservationDetailed reservationDetailed) {
        reservationDetaileds.put(reservationDetailed.getReservation().getId(), getCopy(reservationDetailed));
        return getCopy(reservationDetailed);
    }

    public void clear() {
        reservationDetaileds.clear();
    }

    private static ReservationDetailed getCopy(ReservationDetailed reservationDetailed) {
        return reservationDetailed.toBuilder()
                .reservation(reservationDetailed.getReservation().toBuilder().build())
                .book(reservationDetailed.getBook().toBuilder().build())
                .user(reservationDetailed.getUser().toBuilder().build())
                .build();
    }
}
