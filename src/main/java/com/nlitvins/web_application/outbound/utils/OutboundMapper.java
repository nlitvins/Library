package com.nlitvins.web_application.outbound.utils;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.Reservation;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class OutboundMapper {

    public static class Books {
        public static BookEntity toEntity(Book book) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setId(book.getId());
            bookEntity.setTitle(book.getTitle());
            bookEntity.setAuthor(Integer.parseInt(book.getAuthor()));
            bookEntity.setQuantity(book.getQuantity());
            return bookEntity;
        }

        public static Book toDomain(BookEntity bookEntity) {
            Book book = new Book();
            book.setId(bookEntity.getId());
            book.setTitle(bookEntity.getTitle());
            book.setAuthor(String.valueOf(bookEntity.getAuthor()));
            book.setQuantity(bookEntity.getQuantity());
            return book;
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

    public static class Reservations {
        public static Reservation toDomain(ReservationEntity reservationEntity) {
            return new Reservation(
                    reservationEntity.getId(),
                    reservationEntity.getUserId(),
                    reservationEntity.getBookId(),
                    reservationEntity.getCreatedDate(),
                    reservationEntity.getTermDate(),
                    reservationEntity.getUpdatedDate(),
                    reservationEntity.getStatus(),
                    reservationEntity.getExtensionCount()
            );
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
    }

    public static class Users {

        public static User toDomain(UserEntity userEntity) {
            User user = new User();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setSecondName(userEntity.getSecondName());
            user.setUserName(userEntity.getUserName());
            user.setPassword(userEntity.getPassword());
            user.setEmail(userEntity.getEmail());
            user.setMobileNumber(userEntity.getMobileNumber());
            user.setPersonCode(userEntity.getPersonCode());

            return user;

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

}


