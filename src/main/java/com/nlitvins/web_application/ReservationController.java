package com.nlitvins.web_application;

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

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationEntity> reservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return Mapper.reservationToList(reservationEntities);
    }

    @PostMapping
    public ReservationResponse reserveBook(@RequestBody ReservationCreateRequest request) {
        ReservationEntity reservationEntity = new ReservationEntity();
        LocalDateTime dateTime = LocalDateTime.now();

        LocalDateTime termDate = LocalDate.now().atStartOfDay().minusSeconds(1);

        reservationEntity.setUserId(request.getUserId());
        reservationEntity.setBookId(request.getBookId());
        reservationEntity.setCreatedDate(dateTime);
        reservationEntity.setTermDate(termDate.plusDays(4));
        reservationEntity.setUpdatedDate(dateTime);
        reservationEntity.setStatus(ReservationStatus.NEW.id);
        reservationEntity.setExtensionCount((short) 0);

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return Mapper.getReservationResponse(savedReservationEntity);
    }

    @PutMapping("/{id}/receiving")
    public ReservationResponse receiveBook(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        if (reservationEntity.getStatus() == ReservationStatus.NEW.id) {
            LocalDateTime dateTime = LocalDateTime.now();
            reservationEntity.setTermDate(dateTime.plusDays(14));
            reservationEntity.setStatus(ReservationStatus.RECEIVED.id);
            reservationEntity.setExtensionCount((short) 0);

            ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

            return Mapper.getReservationResponse(savedReservationEntity);
        } else {
            throw new RuntimeException("You can't receive the book. Incorrect status.");
        }
    }

    @PutMapping("/{id}/extension")
    public ReservationResponse extent(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        LocalDateTime termDate = reservationEntity.getTermDate();
        LocalDateTime updatedDate = LocalDateTime.now();
        short newExtensionCount = (short) (reservationEntity.getExtensionCount() + 1);

        if (reservationEntity.getStatus() == ReservationStatus.NEW.id && newExtensionCount < 2) {
            reservationEntity.setTermDate(termDate.plusDays(3));
        } else if (reservationEntity.getStatus() == ReservationStatus.RECEIVED.id && newExtensionCount < 4) {
            reservationEntity.setTermDate(termDate.plusDays(14));
        } else {
            throw new RuntimeException("You can't extend reservation. Incorrect status or extension count.");
        }
        reservationEntity.setUpdatedDate(updatedDate);
        reservationEntity.setExtensionCount(newExtensionCount);
        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        return Mapper.getReservationResponse(savedReservationEntity);
    }

    @PutMapping("/{id}/completed")
    public ReservationResponse complete(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        if (reservationEntity.getStatus() == ReservationStatus.RECEIVED.id) {
            reservationEntity.setStatus(ReservationStatus.COMPLETED.id);
            reservationEntity.setUpdatedDate(LocalDateTime.now());

            ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
            return Mapper.getReservationResponse(savedReservationEntity);
        } else {
            throw new RuntimeException("You can't complete reservation. Incorrect status.");
        }
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        if (reservationEntity.getStatus() == ReservationStatus.NEW.id) {
            reservationEntity.setStatus(ReservationStatus.CANCELED.id);
            reservationEntity.setUpdatedDate(LocalDateTime.now());

            ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
            return Mapper.getReservationResponse(savedReservationEntity);
        } else {
            throw new RuntimeException("You can't cancel reservation. Incorrect status.");
        }
    }

    @PutMapping("/{id}/lost")
    public ReservationResponse lost(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        reservationEntity.setStatus(ReservationStatus.LOST.id);
        reservationEntity.setUpdatedDate(LocalDateTime.now());

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        return Mapper.getReservationResponse(savedReservationEntity);
    }

    @DeleteMapping("/{id}/deleting")
    public ResponseEntity<Void> reservationDelete(@PathVariable int id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
