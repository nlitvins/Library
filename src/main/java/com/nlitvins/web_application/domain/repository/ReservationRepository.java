package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Reservation findById(int id);

    Reservation findByBookIdAndUserId(int bookId, int userId);

    List<Reservation> findByUserId(int id);

    List<Reservation> findByUserIdAndStatusIn(int id, List<Short> statuses);
}
