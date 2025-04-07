package com.nlitvins.web_application.inbound.utils;

import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.model.UserResponse;

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

    public static class Users {

        public static UserResponse toDTO(User user) {
            return new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getSecondName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getMobileNumber(),
                    user.getPersonCode()
            );
        }

        public static List<UserResponse> toDTOList(List<User> users) {
            List<UserResponse> userResponses = new ArrayList<>();
            for (int index = 0; index < users.size(); index++) {
                User user = users.get(index);
                UserResponse response = toDTO(user);
                userResponses.add(response);
            }
            return userResponses;
        }


    }
}
