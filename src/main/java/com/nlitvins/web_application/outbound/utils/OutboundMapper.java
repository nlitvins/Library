package com.nlitvins.web_application.outbound.utils;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.BookGenre;
import com.nlitvins.web_application.domain.model.BookStatus;
import com.nlitvins.web_application.domain.model.BookType;
import com.nlitvins.web_application.domain.model.IsbnBook;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.ReservationStatus;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.model.UserRole;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.model.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class OutboundMapper {

    @UtilityClass
    public static class Books {
        public static BookEntity toEntity(Book book) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setId(book.getId());
            bookEntity.setTitle(book.getTitle());
            bookEntity.setAuthor(book.getAuthor());
            bookEntity.setQuantity(book.getQuantity());
            bookEntity.setCreationYear(book.getCreationYear());
            bookEntity.setStatus(book.getStatus().id);
            bookEntity.setGenre(book.getGenre().id);
            bookEntity.setPages(book.getPages());
            bookEntity.setEdition(book.getEdition());
            bookEntity.setReleaseDate(book.getReleaseDate());
            bookEntity.setType(book.getType().id);
            return bookEntity;
        }

        public static Book toDomain(BookEntity bookEntity) {

            return Book.builder()
                    .id(bookEntity.getId())
                    .title(bookEntity.getTitle())
                    .author(bookEntity.getAuthor())
                    .quantity(bookEntity.getQuantity())
                    .creationYear(bookEntity.getCreationYear())
                    .status(BookStatus.getStatus(bookEntity.getStatus()))
                    .genre(BookGenre.getGenre(bookEntity.getGenre()))
                    .pages(bookEntity.getPages())
                    .edition(bookEntity.getEdition())
                    .releaseDate(bookEntity.getReleaseDate())
                    .type(BookType.getType(bookEntity.getType()))
                    .build();
        }

        public static List<Book> toDomainList(List<BookEntity> bookEntities) {

            List<Book> books = new ArrayList<>();
            for (int index = bookEntities.size() - 1; index >= 0; index--) {
                BookEntity get = bookEntities.get(index);
                Book mapper = toDomain(get);
                books.add(mapper);
            }
            return books;
        }
    }

    @UtilityClass
    public static class Reservations {
        public static Reservation toDomain(ReservationEntity reservationEntity) {
            if (reservationEntity == null) {
                return null;
            }
            return Reservation.builder()
                    .id(reservationEntity.getId())
                    .userId(reservationEntity.getUserId())
                    .bookId(reservationEntity.getBookId())
                    .createdDate(reservationEntity.getCreatedDate())
                    .termDate(reservationEntity.getTermDate())
                    .updatedDate(reservationEntity.getUpdatedDate())
                    .status(ReservationStatus.getStatus(reservationEntity.getStatus()))
                    .extensionCount(reservationEntity.getExtensionCount())
                    .build();
        }

        public static List<Reservation> toDomainList(List<ReservationEntity> reservationEntities) {

            List<Reservation> reservations = new ArrayList<>();
            for (int index = reservationEntities.size() - 1; index >= 0; index--) {
                ReservationEntity get = reservationEntities.get(index);
                Reservation mapper = toDomain(get);
                reservations.add(mapper);
            }
            return reservations;
        }

        public static ReservationEntity toEntity(Reservation reservation) {
            ReservationEntity reservationEntity = new ReservationEntity();

            reservationEntity.setId(reservation.getId());
            reservationEntity.setUserId(reservation.getUserId());
            reservationEntity.setBookId(reservation.getBookId());
            reservationEntity.setCreatedDate(reservation.getCreatedDate());
            reservationEntity.setTermDate(reservation.getTermDate());
            reservationEntity.setStatus(reservation.getStatus().id);
            reservationEntity.setExtensionCount(reservation.getExtensionCount());

            return reservationEntity;
        }
    }

    @UtilityClass
    public static class Users {

        public static User toDomain(UserEntity userEntity) {
            return User.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .secondName(userEntity.getSecondName())
                    .userName(userEntity.getUserName())
                    .password(userEntity.getPassword())
                    .email(userEntity.getEmail())
                    .mobileNumber(userEntity.getMobileNumber())
                    .personCode(userEntity.getPersonCode())
                    .role(UserRole.getRole(userEntity.getRole()))
                    .build();

        }

        public static UserEntity toEntity(User user) {
            UserEntity userEntity = new UserEntity();

            userEntity.setId(user.getId());
            userEntity.setName(user.getName());
            userEntity.setSecondName(user.getSecondName());
            userEntity.setUserName(user.getUserName());
            userEntity.setPassword(user.getPassword());
            userEntity.setEmail(user.getEmail());
            userEntity.setMobileNumber(user.getMobileNumber());
            userEntity.setPersonCode(user.getPersonCode());
            userEntity.setRole(user.getRole().id);

            return userEntity;
        }

        public static List<User> toDomainList(List<UserEntity> userEntities) {
            List<User> users = new ArrayList<>();
            for (int index = 0; index < userEntities.size(); index++) {
                UserEntity get = userEntities.get(index);
                User mapper = toDomain(get);
                users.add(mapper);
            }
            return users;
        }
    }

    @UtilityClass
    public static class IsbnBooks {
        public static BookEntity toEntity(Book book) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setTitle(book.getTitle());
            bookEntity.setAuthor(book.getAuthor());

            return bookEntity;
        }

        public static IsbnBook toDomain(BookEntity bookEntity) {
            return IsbnBook.builder()
                    .title(bookEntity.getTitle())
                    .authors(bookEntity.getAuthor())
                    .build();

        }
    }

    public static class ReservationStatuses {
        public static List<Short> getShorts(List<ReservationStatus> statuses) {
            List<Short> shorts = new ArrayList<>();
            for (ReservationStatus reservationStatus : statuses) {
                shorts.add(reservationStatus.id);
            }
            return shorts;
        }
    }
}



