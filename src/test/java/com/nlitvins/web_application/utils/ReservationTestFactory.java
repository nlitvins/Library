package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.*;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationTestFactory {

    public static List<Reservation> givenReservations() {
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(givenReservation());
        reservations.add(givenReservation());
        return reservations;
    }

    public static List<ReservationResponse> givenReservationResponses() {
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        reservationResponses.add(givenReservationResponse());
        reservationResponses.add(givenReservationResponse());
        return reservationResponses;
    }

    public static Reservation givenReservation() {
        return Reservation.builder()
                .id(1)
                .userId(2)
                .bookId(book().getId())
                .createdDate(LocalDateTime.now())
                .termDate(LocalDateTime.now())
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public static Reservation givenReservation(int id) {
        return Reservation.builder()
                .id(id)
                .userId(2)
                .bookId(book().getId())
                .createdDate(LocalDateTime.now())
                .termDate(LocalDateTime.now())
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public static ReservationResponse givenReservationResponse() {
        return ReservationResponse.builder()
                .userId(2)
                .bookId(book().getId())
                .createdDate(LocalDateTime.now())
                .termDate(LocalDateTime.now())
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public static ReservationResponse givenReservationResponse(int id) {
        return ReservationResponse.builder()
                .id(id)
                .userId(2)
                .bookId(book().getId())
                .createdDate(LocalDateTime.now())
                .termDate(LocalDateTime.now())
                .status(ReservationStatus.NEW)
                .extensionCount((short) 0)
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public static ReservationCreateRequest givenReservationCreateRequest() {
        return ReservationCreateRequest.builder()
                .userId(2)
                .bookId(3)
                .build();
    }

    public static Book book() {
        return Book.builder()
                .id(3)
                .title("Test Book")
                .author("Test Author")
                .quantity(4)
                .creationYear(LocalDate.parse("1888-04-03"))
                .status(BookStatus.AVAILABLE)
                .genre(BookGenre.ADVENTURE)
                .pages((short) 444)
                .edition("Test-edition")
                .releaseDate(LocalDate.parse("2021-10-09"))
                .type(BookType.BOOK)
                .build();
    }
}
