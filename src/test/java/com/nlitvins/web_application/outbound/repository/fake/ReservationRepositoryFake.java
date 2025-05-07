package com.nlitvins.web_application.outbound.repository.fake;


import com.nlitvins.web_application.domain.model.Reservation;
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
        return reservations.get(searchId);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Reservation findByBookIdAndUserId(int bookId, int userId) {
        List<Reservation> values = reservations.values().stream().toList();
        for (Reservation reservation : values) {
            if (reservation.getUserId() == userId && reservation.getBookId() == bookId) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public List<Reservation> findByUserId(int userId) {
        List<Reservation> results = new ArrayList<>();
        List<Reservation> values = reservations.values().stream().toList();
        for (Reservation reservation : values) {
            if (reservation.getUserId() == userId) {
                results.add(reservation);
            }
        }
        return results;
    }

    @Override
    public List<Reservation> findByUserIdAndStatusIn(int userId, List<Short> statuses) {
        List<Reservation> results = new ArrayList<>();
        List<Reservation> values = reservations.values().stream().toList();
        for (Reservation reservation : values) {
            if (reservation.getUserId() == userId && statuses.contains(reservation.getStatus())) {
                results.add(reservation);
            }
        }
        return results;
    }

    public void clear() {
        reservations.clear();
    }
}
