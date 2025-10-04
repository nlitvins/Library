package com.nlitvins.web_application.inbound.rest.reservation;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.usecase.reservation.ReservationCreateUseCase;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationCreateController {

    private final ReservationCreateUseCase reservationCreateUseCase;

    public ReservationCreateController(ReservationCreateUseCase reservationCreateUseCase) {
        this.reservationCreateUseCase = reservationCreateUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReservationResponse reserveBook(@RequestBody ReservationCreateRequest request) {
        Reservation reservation = InboundMapper.Reservations.toDomain(request);
        Reservation savedReservation = reservationCreateUseCase.registerReservation(reservation);
        return InboundMapper.Reservations.toDTO(savedReservation);
    }
}
