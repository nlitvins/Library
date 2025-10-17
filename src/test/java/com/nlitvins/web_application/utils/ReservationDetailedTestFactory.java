package com.nlitvins.web_application.utils;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.inbound.model.ReservationDetailedResponse;

import java.util.ArrayList;
import java.util.List;

import static com.nlitvins.web_application.utils.BookTestFactory.givenBook;
import static com.nlitvins.web_application.utils.BookTestFactory.givenResponseBook;
import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservation;
import static com.nlitvins.web_application.utils.ReservationTestFactory.givenReservationResponse;
import static com.nlitvins.web_application.utils.UserTestFactory.givenUser;
import static com.nlitvins.web_application.utils.UserTestFactory.givenUserResponse;

public class ReservationDetailedTestFactory {

    public static List<ReservationDetailedResponse> givenReservationDetailedResponseList() {
        List<ReservationDetailedResponse> reservationDetailedResponseList = new ArrayList<>();
        reservationDetailedResponseList.add(givenReservationDetailedResponse(1, 2));
        reservationDetailedResponseList.add(givenReservationDetailedResponse(2, 3));
        return reservationDetailedResponseList;
    }

    public static List<ReservationDetailed> givenReservationDetailedList() {
        List<ReservationDetailed> reservationDetailedList = new ArrayList<>();
        reservationDetailedList.add(givenReservationDetailed(1, 2));
        reservationDetailedList.add(givenReservationDetailed(2, 3));
        return reservationDetailedList;
    }

    public static ReservationDetailedResponse givenReservationDetailedResponse(int reservationId, int bookId) {
        return ReservationDetailedResponse.builder()
                .reservation(givenReservationResponse(reservationId))
                .book(givenResponseBook(bookId))
                .user(givenUserResponse())
                .build();
    }

    public static ReservationDetailed givenReservationDetailed(int reservationId, int bookId) {
        return ReservationDetailed.builder()
                .reservation(givenReservation(reservationId))
                .book(givenBook(bookId))
                .user(givenUser())
                .build();
    }
}
