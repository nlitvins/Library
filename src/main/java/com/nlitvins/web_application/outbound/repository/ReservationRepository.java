package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.outbound.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    List<ReservationEntity> findByStatusInAndTermDateBefore(Collection<Short> status, LocalDateTime termDate);
}
