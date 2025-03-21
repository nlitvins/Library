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

        reservationEntity.setId(reservationEntity.getId());
        reservationEntity.setUserId(reservationEntity.getUserId());
        reservationEntity.setBookId(reservationEntity.getBookId());
        reservationEntity.setCreatedDate(reservationEntity.getCreatedDate());
        reservationEntity.setTermDate(reservationEntity.getTermDate());
        reservationEntity.setStatus(reservationEntity.getStatus());
        reservationEntity.setUpdatedDate(reservationEntity.getUpdatedDate());

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
        return savedReservationEntity;

    }
}
