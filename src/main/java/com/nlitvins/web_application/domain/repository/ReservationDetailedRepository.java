package com.nlitvins.web_application.domain.repository;

import com.nlitvins.web_application.domain.model.ReservationDetailed;

import java.util.List;

public interface ReservationDetailedRepository {

    List<ReservationDetailed> findAll();

}
