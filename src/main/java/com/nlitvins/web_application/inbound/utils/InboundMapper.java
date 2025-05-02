package com.nlitvins.web_application.inbound.utils;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.inbound.model.BookRequest;
import com.nlitvins.web_application.inbound.model.BookResponse;
import com.nlitvins.web_application.inbound.model.ReservationCreateRequest;
import com.nlitvins.web_application.inbound.model.ReservationResponse;
import com.nlitvins.web_application.inbound.model.UserRequest;
import com.nlitvins.web_application.inbound.model.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        public static Reservation toDomain(ReservationCreateRequest request) {
            Reservation reservation = new Reservation();
            LocalDateTime dateTime = LocalDateTime.now();
            LocalDateTime termDate = LocalDate.now().atStartOfDay().minusNanos(1);

            reservation.setUserId(request.getUserId());
            reservation.setBookId(request.getBookId());
            reservation.setCreatedDate(dateTime);
            reservation.setTermDate(termDate.plusDays(4)); //Real term is 3 days
            reservation.setUpdatedDate(dateTime);
            reservation.setStatus(ReservationStatus.NEW.id);
            reservation.setExtensionCount((short) 0);

            return reservation;
        }
    }

    public static class Users {

        public static User toDomain(UserRequest request) {
            User user = new User();
            user.setId(request.getId());
            user.setName(request.getName());
            user.setSecondName(request.getSecondName());
            user.setUserName(request.getUserName());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setMobileNumber(request.getMobileNumber());
            user.setPersonCode(request.getPersonCode());

            return user;
        }

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

    public static class Books {
        public static Book toDomain(BookRequest request) {
            Book book = new Book();

            book.setId(request.getId());
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setQuantity(request.getQuantity());

            return book;
        }


        public static BookResponse toDTO(Book book) {
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getQuantity()
            );
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
}
