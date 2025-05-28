package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Reservation findById(int id);

    List<Reservation> findByUserIdAndStatusIn(int id, List<ReservationStatus> statuses);

    List<Reservation> findByUserIdAndBookIdAndStatusIn(int userId, int bookId, List<ReservationStatus> statuses);

    List<Reservation> findByUserId(int userId);
}
