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
        reservationDetailedResponseList.add(givenReservationDetailedResponse());
        reservationDetailedResponseList.add(givenReservationDetailedResponse());
        return reservationDetailedResponseList;
    }

    public static List<ReservationDetailed> givenReservationDetailedList() {
        List<ReservationDetailed> reservationDetailedList = new ArrayList<>();
        reservationDetailedList.add(givenReservationDetailed());
        reservationDetailedList.add(givenReservationDetailed());
        return reservationDetailedList;
    }

    public static ReservationDetailedResponse givenReservationDetailedResponse() {
        return ReservationDetailedResponse.builder()
                .reservation(givenReservationResponse())
                .book(givenResponseBook())
                .user(givenUserResponse())
                .build();
    }

    public static ReservationDetailed givenReservationDetailed() {
        return ReservationDetailed.builder()
                .reservation(givenReservation())
                .book(givenBook())
                .user(givenUser())
                .build();
    }
}
