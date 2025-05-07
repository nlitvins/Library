package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.repository.ReservationRepository;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.repository.jpa.ReservationJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository jpaRepository;

    public ReservationRepositoryImpl(ReservationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Reservation> findAll() {
        List<ReservationEntity> reservationEntities = jpaRepository.findAll();
        return OutboundMapper.Reservations.toDomainList(reservationEntities);
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = OutboundMapper.Reservations.toEntity(reservation);
        ReservationEntity savedReservationEntity = jpaRepository.save(reservationEntity);
        return OutboundMapper.Reservations.toDomain(savedReservationEntity);
    }

    @Override
    public Reservation findById(int id) {
        ReservationEntity reservationEntity = jpaRepository.getReferenceById(id);
        return OutboundMapper.Reservations.toDomain(reservationEntity);
    }

    @Override
    public Reservation findByBookIdAndUserId(int bookId, int userId) {
        ReservationEntity reservationEntity = jpaRepository.findByBookIdAndUserId(bookId, userId);
        return OutboundMapper.Reservations.toDomain(reservationEntity);
    }

    @Override
    public List<Reservation> findByUserId(int id) {
        List<ReservationEntity> reservationEntity = jpaRepository.findByUserId(id);
        return OutboundMapper.Reservations.toDomainList(reservationEntity);
    }

    @Override
    public List<Reservation> findByUserIdAndStatusIn(int id, List<ReservationStatus> statuses) {
        List<Short> results = OutboundMapper.ReservationStatuses.getShorts(statuses);
        List<ReservationEntity> reservationEntity = jpaRepository.findByUserIdAndStatusIn(id, results);
        return OutboundMapper.Reservations.toDomainList(reservationEntity);
    }

    @Override
    public List<Reservation> findByUserIdAndBookIdAndStatusIn(int userId, int bookId, List<ReservationStatus> statuses) {
        List<Short> results = OutboundMapper.ReservationStatuses.getShorts(statuses);
        List<ReservationEntity> reservationEntity = jpaRepository.findByUserIdAndBookIdAndStatusIn(userId, bookId, results);
        return OutboundMapper.Reservations.toDomainList(reservationEntity);
    }
}
