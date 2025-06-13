package com.nlitvins.web_application.inbound.rest;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.usecase.ReservationDetailedUseCase;
import com.nlitvins.web_application.inbound.model.ReservationDetailedResponse;
import com.nlitvins.web_application.inbound.utils.InboundMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<ReservationDetailedResponse> findAll() {
        List<ReservationDetailed> reservationDetailedList = reservationDetailedUseCase.findAll();
        return InboundMapper.ReservationsDetailed.toDTOList(reservationDetailedList);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDetailedResponse> findByUserId(@PathVariable int userId) {
        List<ReservationDetailed> reservationDetailedList = reservationDetailedUseCase.findByUserId(userId);
        return InboundMapper.ReservationsDetailed.toDTOList(reservationDetailedList);
    }
}
