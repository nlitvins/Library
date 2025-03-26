package com.nlitvins.web_application;


import org.springframework.web.bind.annotation.GetMapping;
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
    public ReservationResponse receiveBook(@RequestBody ReservationRequest request) {
        ReservationEntity reservationEntity = new ReservationEntity();
        LocalDateTime dateTime = LocalDateTime.now();

        reservationEntity.setId(request.getId());
        reservationEntity.setUserId(request.getUserId());
        reservationEntity.setBookId(request.getBookId());
        reservationEntity.setCreatedDate(dateTime);
        reservationEntity.setTermDate(dateTime.plusDays(14));
        reservationEntity.setUpdatedDate(dateTime);
        reservationEntity.setStatus((short) 2);
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


//    @DeleteMapping
//    public ResponseEntity<Void> reservationCanceling(@PathVariable int Id) {
//        reservationRepository.deleteById(Id);
//        return ResponseEntity.noContent().build();
//    }
}
