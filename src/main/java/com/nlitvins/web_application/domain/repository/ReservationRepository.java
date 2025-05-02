package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Reservation findById(int id);

    Reservation findByBookIdAndUserId(int bookId, int userId);

//    Reservation exists();
}
