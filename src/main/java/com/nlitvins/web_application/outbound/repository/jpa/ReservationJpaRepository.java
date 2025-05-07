package com.nlitvins.web_application.outbound.repository.jpa;

import com.nlitvins.web_application.outbound.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Integer> {

    List<ReservationEntity> findByStatusAndTermDateBefore(Short status, LocalDateTime termDate);

    ReservationEntity findByBookIdAndUserId(int bookId, int userId);

    List<ReservationEntity> findByUserId(int id);

    List<ReservationEntity> findByUserIdAndStatusIn(int id, List<Short> statuses);

    List<ReservationEntity> findByUserIdAndBookIdAndStatusIn(int userId, int bookId, List<Short> statuses);
}
