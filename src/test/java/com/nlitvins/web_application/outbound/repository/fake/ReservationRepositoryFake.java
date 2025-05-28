package com.nlitvins.web_application.outbound.repository.fake;


import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ReservationRepositoryFake implements ReservationRepository {

    private final HashMap<Integer, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation findById(int searchId) {
        Reservation reservation = reservations.get(searchId);
        return reservation != null ? reservation.toBuilder().build() : null;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().map(it -> it.toBuilder().build()).toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation.toBuilder().build();
    }

    @Override
    public List<Reservation> findByUserIdAndStatusIn(int userId, List<ReservationStatus> statuses) {
        List<Reservation> results = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId() == userId && statuses.contains(reservation.getStatus())) {
                results.add(reservation.toBuilder().build());
            }
        }
        return results;
    }

    @Override
    public List<Reservation> findByUserIdAndBookIdAndStatusIn(int userId, int bookId, List<ReservationStatus> statuses) {
        List<Reservation> results = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId() == userId && reservation.getBookId() == bookId && statuses.contains(reservation.getStatus())) {
                results.add(reservation.toBuilder().build());
            }
        }
        return results;
    }

    @Override
    public List<Reservation> findByUserId(int userId) {
        List<Reservation> results = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId() == userId) {
                results.add(reservation.toBuilder().build());
            }
        }
        return results;
    }

    public void clear() {
        reservations.clear();
    }
}
