package com.nlitvins.web_application.inbound.rest.reservation_detailed;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.usecase.reservation_detailed.ReservationDetailedUseCase;
import com.nlitvins.web_application.inbound.model.ReservationDetailedResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations/detailed", produces = APPLICATION_JSON_VALUE)
public class ReservationDetailedController {

    private final ReservationDetailedUseCase reservationDetailedUseCase;

    public ReservationDetailedController(ReservationDetailedUseCase reservationDetailedUseCase) {
        this.reservationDetailedUseCase = reservationDetailedUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<ReservationDetailedResponse> findAll() {
        List<ReservationDetailed> reservationDetailedList = reservationDetailedUseCase.getAll();
        return InboundMapper.ReservationsDetailed.toDTOList(reservationDetailedList);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ReservationDetailedResponse> reservationDetailedByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        List<ReservationDetailed> reservationDetailed = reservationDetailedUseCase.getReservationsByToken(extractToken(authHeader));
        return InboundMapper.ReservationsDetailed.toDTOList(reservationDetailed);

    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public List<ReservationDetailedResponse> findByUserId(@PathVariable int userId) {
        List<ReservationDetailed> reservationDetailedList = reservationDetailedUseCase.getByUserId(userId);
        return InboundMapper.ReservationsDetailed.toDTOList(reservationDetailedList);
    }

    private String extractToken(String header) {
        return header != null && header.startsWith("Bearer ")
                ? header.substring(7)
                : null;
    }
}
