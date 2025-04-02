package com.nlitvins.web_application;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static BookEntity bookToEntity(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(book.getId());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(Integer.parseInt(book.getAuthor()));
        bookEntity.setQuantity(book.getQuantity());
        return bookEntity;
    }

    public static Book entityToBook(BookEntity bookEntity) {
        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(String.valueOf(bookEntity.getAuthor()));
        book.setQuantity(bookEntity.getQuantity());
        return book;
    }

    public static Reservation entityToReservation(ReservationEntity reservationEntity) {
        Reservation reservation = new Reservation();

        reservation.setId(reservationEntity.getId());
        reservation.setUserId(reservationEntity.getUserId());
        reservation.setBookId(reservationEntity.getBookId());
        reservation.setCreatedDate(reservationEntity.getCreatedDate());
        reservation.setTermDate(reservationEntity.getTermDate());
        reservation.setStatus(reservationEntity.getStatus());
        reservation.setExtensionCount(reservationEntity.getExtensionCount());
        reservation.setUpdatedDate(reservationEntity.getUpdatedDate());
        return reservation;
    }


    public static List<Book> bookToList(List<BookEntity> bookEntities) {

        List<Book> books = new ArrayList<>();
        for (int index = bookEntities.size() - 1; index >= 0; index--) {
            BookEntity get = bookEntities.get(index);
            Book mapper = entityToBook(get);
            books.add(mapper);
        }
        return books;
    }

    public static List<UserEntity> userToList(List<UserEntity> userEntities) {

        List<UserEntity> users = new ArrayList<>();
        for (int index = userEntities.size() - 1; index >= 0; index--) {
            UserEntity get = userEntities.get(index);
            users.add(get);
        }
        return users;
    }

    public static List<ReservationEntity> reservationToList(List<ReservationEntity> reservationEntities) {

        List<ReservationEntity> reservations = new ArrayList<>();
        for (int index = reservationEntities.size() - 1; index >= 0; index--) {
            ReservationEntity get = reservationEntities.get(index);
            reservations.add(get);
        }
        return reservations;
    }

    public static ReservationResponse getReservationResponse(ReservationEntity savedReservationEntity) {
        return new ReservationResponse(
                savedReservationEntity.getId(),
                savedReservationEntity.getUserId(),
                savedReservationEntity.getBookId(),
                savedReservationEntity.getCreatedDate(),
                savedReservationEntity.getTermDate(),
                savedReservationEntity.getUpdatedDate(),
                savedReservationEntity.getStatus(),
                savedReservationEntity.getExtensionCount()
        );
    }
}
