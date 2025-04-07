package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.repository.jpa.ReservationJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationController {

    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationController(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }

    @GetMapping
    public List<ReservationResponse> reservations() {
        List<ReservationEntity> reservationEntities = reservationJpaRepository.findAll();
        List<Reservation> get = OutboundMapper.Reservations.toDomainList(reservationEntities);
        return InboundMapper.Reservations.toDTOList(get);
    }

    @PostMapping
    public ReservationResponse reserveBook(@RequestBody ReservationCreateRequest request) {
        ReservationEntity reservationEntity = new ReservationEntity();
        LocalDateTime dateTime = LocalDateTime.now();

        LocalDateTime termDate = LocalDate.now().atStartOfDay().minusNanos(1);

        reservationEntity.setUserId(request.getUserId());
        reservationEntity.setBookId(request.getBookId());
        reservationEntity.setCreatedDate(dateTime);
        reservationEntity.setTermDate(termDate.plusDays(4)); //Real term is 3 days
        reservationEntity.setUpdatedDate(dateTime);
        reservationEntity.setStatus(ReservationStatus.NEW.id);
        reservationEntity.setExtensionCount((short) 0);

        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);

        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/receiving")
    public ReservationResponse receiveBook(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReferenceById(id);

        if (reservationEntity.getStatus() != ReservationStatus.NEW.id) {
            throw new RuntimeException("You can't receive the book. Incorrect status.");
        }

        LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusNanos(1);
        reservationEntity.setTermDate(dateTime.plusDays(15)); //Real term is 14 days
        reservationEntity.setStatus(ReservationStatus.RECEIVED.id);
        reservationEntity.setExtensionCount((short) 0);

        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);

        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/extension")
    public ReservationResponse extent(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReferenceById(id);
        LocalDateTime termDate = reservationEntity.getTermDate();
        short extensionCount = reservationEntity.getExtensionCount();

        if (reservationEntity.getStatus() == ReservationStatus.NEW.id && extensionCount < 1) {
            reservationEntity.setTermDate(termDate.plusDays(3));
        } else if (reservationEntity.getStatus() == ReservationStatus.RECEIVED.id && extensionCount < 3) {
            reservationEntity.setTermDate(termDate.plusDays(14));
        } else {
            throw new RuntimeException("You can't extend reservation. Incorrect status or extension count.");
        }

        LocalDateTime updatedDate = LocalDateTime.now();
        reservationEntity.setUpdatedDate(updatedDate);
        reservationEntity.setExtensionCount((short) (extensionCount + 1));
        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);
        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/completed")
    public ReservationResponse complete(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReferenceById(id);

        if (reservationEntity.getStatus() != ReservationStatus.RECEIVED.id) {
            throw new RuntimeException("You can't complete reservation. Incorrect status.");
        }
        reservationEntity.setStatus(ReservationStatus.COMPLETED.id);
        reservationEntity.setUpdatedDate(LocalDateTime.now());

        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);
        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReferenceById(id);

        if (reservationEntity.getStatus() != ReservationStatus.NEW.id) {
            throw new RuntimeException("You can't cancel reservation. Incorrect status.");
        }

        reservationEntity.setStatus(ReservationStatus.CANCELED.id);

        reservationEntity.setUpdatedDate(LocalDateTime.now());

        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);
        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/lost")
    public ReservationResponse lost(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationJpaRepository.getReferenceById(id);

        reservationEntity.setStatus(ReservationStatus.LOST.id);
        reservationEntity.setUpdatedDate(LocalDateTime.now());

        ReservationEntity savedReservationEntity = reservationJpaRepository.save(reservationEntity);
        Reservation reservation = OutboundMapper.Reservations.toDomain(savedReservationEntity);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @DeleteMapping("/{id}/deleting")
    public ResponseEntity<Void> reservationDelete(@PathVariable int id) {
        reservationJpaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
