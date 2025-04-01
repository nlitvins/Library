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

        reservationEntity.setUserId(request.getUserId());
        reservationEntity.setBookId(request.getBookId());
        reservationEntity.setCreatedDate(dateTime);
        reservationEntity.setTermDate(dateTime.plusDays(3));
        reservationEntity.setUpdatedDate(dateTime);
        reservationEntity.setStatus((short) 1);
        reservationEntity.setExtensionCount((short) 0);

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return new ReservationResponse(
                savedReservationEntity.getId(),
                savedReservationEntity.getUserId(),
                savedReservationEntity.getBookId(),
                savedReservationEntity.getCreatedDate(),
                savedReservationEntity.getTermDate(),
                savedReservationEntity.getUpdatedDate(),
                savedReservationEntity.getStatus(),
                savedReservationEntity.getExtensionCount()
        );
    }

    @PutMapping("/{id}/receiving")
    public ReservationResponse receiveBook(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        LocalDateTime dateTime = LocalDateTime.now();
        reservationEntity.setTermDate(dateTime.plusDays(14));
        reservationEntity.setStatus((short) 2);

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return new ReservationResponse(
                savedReservationEntity.getId(),
                savedReservationEntity.getUserId(),
                savedReservationEntity.getBookId(),
                savedReservationEntity.getCreatedDate(),
                savedReservationEntity.getTermDate(),
                savedReservationEntity.getUpdatedDate(),
                savedReservationEntity.getStatus(),
                savedReservationEntity.getExtensionCount()
        );
    }

    @PutMapping("/{id}/extension")
    public ReservationResponse extentReservation(@PathVariable int id) {
        ReservationEntity reservationEntity = reservationRepository.getReferenceById(id);

        LocalDateTime dateTime = LocalDateTime.now();
        short newExtensionCount = (short) (reservationEntity.getExtensionCount() + 1);
        if (newExtensionCount > 3) {
            throw new RuntimeException("You can't extend reservation");
        }

        reservationEntity.setTermDate(dateTime.plusDays(14));
        reservationEntity.setStatus((short) 3);
        reservationEntity.setExtensionCount(newExtensionCount);

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return new ReservationResponse(
                savedReservationEntity.getId(),
                savedReservationEntity.getUserId(),
                savedReservationEntity.getBookId(),
                savedReservationEntity.getCreatedDate(),
                savedReservationEntity.getTermDate(),
                savedReservationEntity.getUpdatedDate(),
                savedReservationEntity.getStatus(),
                savedReservationEntity.getExtensionCount()
        );
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> reservationCanceling(@PathVariable int id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
