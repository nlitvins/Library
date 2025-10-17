package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationTestFactory {

    public static List<Reservation> givenReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(givenReservation(1));
        reservations.add(givenReservation(2));
        return reservations;
    }

    public static List<ReservationResponse> givenReservationResponses() {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservationResponses.add(givenReservationResponse(1));
        reservationResponses.add(givenReservationResponse(2));
        return reservationResponses;
    }

    public static Reservation givenReservation() {
        return Reservation.builder()
                .id(1)
                .userId(2)
                .bookId(3)
                .createdDate(LocalDateTime.parse("2025-05-05T23:59:59.999999999"))
                .termDate(LocalDateTime.parse("2025-07-05T23:59:59.999999999"))
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.parse("2025-06-05T23:59:59.999999999"))
                .build();
    }

    public static Reservation givenReservation(int id) {
        return Reservation.builder()
                .id(id)
                .userId(2)
                .bookId(3)
                .createdDate(LocalDateTime.parse("2025-05-05T23:59:59.999999999"))
                .termDate(LocalDateTime.parse("2025-07-05T23:59:59.999999999"))
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.parse("2025-06-05T23:59:59.999999999"))
                .build();
    }

    public static ReservationResponse givenReservationResponse() {
        return ReservationResponse.builder()
                .id(1)
                .userId(2)
                .bookId(3)

                .createdDate(LocalDateTime.parse("2025-05-05T23:59:59.999999999"))
                .termDate(LocalDateTime.parse("2025-07-05T23:59:59.999999999"))
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.parse("2025-06-05T23:59:59.999999999"))
                .build();
    }

    public static ReservationResponse givenReservationResponse(int id) {
        return ReservationResponse.builder()
                .id(id)
                .userId(2)
                .bookId(3)
                .createdDate(LocalDateTime.parse("2025-05-05T23:59:59.999999999"))
                .termDate(LocalDateTime.parse("2025-07-05T23:59:59.999999999"))
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.parse("2025-06-05T23:59:59.999999999"))
                .build();
    }

    public static ReservationCreateRequest givenReservationCreateRequest() {
        return ReservationCreateRequest.builder()
                .userId(2)
                .bookId(3)
                .build();
    }
}
