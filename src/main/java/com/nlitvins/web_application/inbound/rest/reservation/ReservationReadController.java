package com.nlitvins.web_application.inbound.rest.reservation;


import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationReadUseCase;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationReadController {

    private final ReservationReadUseCase reservationReadUseCase;

    public ReservationReadController(ReservationReadUseCase reservationReadUseCase) {
        this.reservationReadUseCase = reservationReadUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationReadUseCase.getReservations();
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ReservationResponse> reservationsByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Reservation> reservations = reservationReadUseCase.getReservationsByToken(extractToken(token));
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<ReservationResponse> reservationsByUserId(@PathVariable int userId) {
        List<Reservation> reservations = reservationReadUseCase.getReservationByUserId(userId);
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    private String extractToken(String header) {
        return header != null && header.startsWith("Bearer ")
                ? header.substring(7)
                : null;
    }
}
