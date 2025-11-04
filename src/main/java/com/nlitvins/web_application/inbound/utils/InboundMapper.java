package com.nlitvins.web_application.inbound.utils;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.inbound.model.BookCreateRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.model.LoginRequest;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationDetailedResponse;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class InboundMapper {

    @UtilityClass
    public static class Reservations {

        public static ReservationResponse toDTO(Reservation reservation) {
            return ReservationResponse.builder()
                    .id(reservation.getId())
                    .userId(reservation.getUserId())
                    .bookId(reservation.getBookId())
                    .createdDate(reservation.getCreatedDate())
                    .updatedDate(reservation.getUpdatedDate())
                    .termDate(reservation.getTermDate())
                    .status(reservation.getStatus())
                    .extensionCount(reservation.getExtensionCount())
                    .build();
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

        public static Reservation toDomain(ReservationCreateRequest request) {
            return Reservation.builder()
                    .userId(request.getUserId())
                    .bookId(request.getBookId())
                    .status(ReservationStatus.NEW)
                    .extensionCount((short) 0)
                    .build();
        }
    }

    @UtilityClass
    public static class Users {

        public static User toDomain(UserRequest request) {

            return User.builder()
                    .name(request.getName())
                    .secondName(request.getSecondName())
                    .userName(request.getUserName())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .mobileNumber(request.getMobileNumber())
                    .personCode(request.getPersonCode())
                    .build();
        }

        public static User toDomain(LoginRequest request) {
            return User.builder()
                    .userName(request.getUsername())
                    .password(request.getPassword())
                    .build();
        }

        public static UserResponse toDTO(User user) {
            if (user == null) {
                return null;
            }
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .secondName(user.getSecondName())
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .mobileNumber(user.getMobileNumber())
                    .personCode(user.getPersonCode())
                    .role(user.getRole())
                    .build();
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

    @UtilityClass
    public static class Books {
        public static Book toDomain(BookCreateRequest request) {
            return Book.builder()
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .quantity(request.getQuantity())
                    .creationYear(request.getCreationYear())
                    .status(request.getStatus())
                    .genre(request.getGenre())
                    .pages(request.getPages())
                    .edition(request.getEdition())
                    .releaseDate(request.getReleaseDate())
                    .type(request.getType())
                    .isbn(request.getIsbn())
                    .build();
        }


        public static BookResponse toDTO(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .quantity(book.getQuantity())
                    .creationYear(book.getCreationYear())
                    .status(book.getStatus())
                    .genre(book.getGenre())
                    .pages(book.getPages())
                    .edition(book.getEdition())
                    .releaseDate(book.getReleaseDate())
                    .type(book.getType())
                    .isbn(book.getIsbn())
                    .build();
        }

        public static List<BookResponse> toDTOList(List<Book> books) {
            List<BookResponse> bookResponses = new ArrayList<>();
            for (int index = 0; index < books.size(); index++) {
                Book book = books.get(index);
                BookResponse response = toDTO(book);
                bookResponses.add(response);
            }
            return bookResponses;
        }

    }

    @UtilityClass
    public static class ReservationsDetailed {

        public static ReservationDetailedResponse toDTO(ReservationDetailed reservationDetailed) {
            return ReservationDetailedResponse.builder()
                    .reservation(Reservations.toDTO(reservationDetailed.getReservation()))
                    .book(Books.toDTO(reservationDetailed.getBook()))
                    .user(Users.toDTO(reservationDetailed.getUser()))
                    .build();
        }

        public static List<ReservationDetailedResponse> toDTOList(List<ReservationDetailed> reservationDetailedList) {
            List<ReservationDetailedResponse> reservationDetailedResponses = new ArrayList<>();
            for (int index = 0; index < reservationDetailedList.size(); index++) {
                ReservationDetailed reservationDetailed = reservationDetailedList.get(index);
                ReservationDetailedResponse response = toDTO(reservationDetailed);
                reservationDetailedResponses.add(response);
            }
            return reservationDetailedResponses;
        }
    }
}
