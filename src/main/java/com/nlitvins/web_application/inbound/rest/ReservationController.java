package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.ReservationCheckUseCase;
import com.nlitvins.web_application.domain.usecase.ReservationCreateUseCase;
import com.nlitvins.web_application.domain.usecase.ReservationReadUseCase;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationReadUseCase.getReservations();
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ReservationResponse> reservationsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Reservation> reservations = reservationReadUseCase.getReservationsByUserId(extractToken(token));
        return InboundMapper.Reservations.toDTOList(reservations);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReservationResponse reserveBook(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = InboundMapper.Reservations.toDomain(request);
        Reservation savedReservation = reservationCreateUseCase.registerReservation(reservation);
        return InboundMapper.Reservations.toDTO(savedReservation);
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

    private String extractToken(String header) {
        return header != null && header.startsWith("Bearer ")
                ? header.substring(7)
                : null;
    }
}
