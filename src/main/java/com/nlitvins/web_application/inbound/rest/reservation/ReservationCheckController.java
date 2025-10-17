package com.nlitvins.web_application.inbound.rest.reservation;


import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationCheckUseCase;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationCheckController {

    private final ReservationCheckUseCase reservationCheckUseCase;

    public ReservationCheckController(ReservationCheckUseCase reservationCheckUseCase) {
        this.reservationCheckUseCase = reservationCheckUseCase;
    }

    @PutMapping("/{id}/receiving")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ReservationResponse receiveBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.receiveBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/extension")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReservationResponse extentBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.extendBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/completed")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ReservationResponse completeReservation(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.completeReservation(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReservationResponse cancelReservation(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.cancelReservation(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }

    @PutMapping("/{id}/lost")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ReservationResponse loseBook(@PathVariable int id) {
        Reservation reservation = reservationCheckUseCase.loseBook(id);
        return InboundMapper.Reservations.toDTO(reservation);
    }
}
