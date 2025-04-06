package com.nlitvins.web_application.inbound.utils;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.inbound.model.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

public class InboundMapper {

    public static class Reservations {

        public static ReservationResponse toDTO(Reservation reservation) {
            return new ReservationResponse(
                    reservation.getId(),
                    reservation.getUserId(),
                    reservation.getBookId(),
                    reservation.getCreatedDate(),
                    reservation.getTermDate(),
                    reservation.getUpdatedDate(),
                    reservation.getStatus(),
                    reservation.getExtensionCount()
            );
        }

        public static List<ReservationResponse> toDTOList(List<Reservation> reservations) {

            List<ReservationResponse> reservationResponses = new ArrayList<>();
            for (int index = reservations.size() - 1; index >= 0; index--) {
                Reservation get = reservations.get(index);
                ReservationResponse mapper = toDTO(get);
                reservationResponses.add(mapper);
            }
            return reservationResponses;
        }
    }
}
