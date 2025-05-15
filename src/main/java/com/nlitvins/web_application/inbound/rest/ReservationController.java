package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.ReservationCheckUseCase;
import com.nlitvins.web_application.domain.usecase.ReservationCreateUseCase;
import com.nlitvins.web_application.domain.usecase.ReservationReadUseCase;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationController {

    private final ReservationCheckUseCase reservationCheckUseCase;
    private final ReservationReadUseCase reservationReadUseCase;
    private final ReservationCreateUseCase reservationCreateUseCase;

    public ReservationController(ReservationCheckUseCase reservationCheckUseCase, ReservationReadUseCase reservationReadUseCase, ReservationCreateUseCase reservationCreateUseCase) {
        this.reservationCheckUseCase = reservationCheckUseCase;
        this.reservationReadUseCase = reservationReadUseCase;
        this.reservationCreateUseCase = reservationCreateUseCase;
    }

    @GetMapping
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationReadUseCase.getReservations();
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    @PostMapping
    public ReservationResponse reserveBook(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = InboundMapper.Reservations.toDomain(request);
        Reservation savedReservation = reservationCreateUseCase.registerReservation(reservation);
        return InboundMapper.Reservations.toDTO(savedReservation);
    }

    @PutMapping("/{id}/receiving")
    public ReservationResponse receiveBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.receiveBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/extension")
    public ReservationResponse extentBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.extendBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/completed")
    public ReservationResponse completeReservation(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.completeReservation(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponse cancelReservation(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.cancelReservation(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/lost")
    public ReservationResponse loseBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.loseBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }
}
