package com.nlitvins.web_application;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Reservation reserveBook(@RequestBody Reservation reservation) {
        ReservationEntity reservationEntity = new ReservationEntity();

        reservationEntity.setId(reservation.getId());
        reservationEntity.setUserId(reservation.getUserId());
        reservationEntity.setBookId(reservation.getBookId());
        reservationEntity.setCreatedDate(reservation.getCreatedDate());
        reservationEntity.setTermDate(reservation.getTermDate());
        reservationEntity.setStatus(reservation.getStatus());
        reservationEntity.setExtensionCount(reservation.getExtensionCount());
        reservationEntity.setUpdatedDate(reservation.getUpdatedDate());

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        return Mapper.entityToReservation(savedReservationEntity);

    }
}
