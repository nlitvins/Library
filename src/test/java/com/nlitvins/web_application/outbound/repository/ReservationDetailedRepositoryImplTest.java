package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.model.UserEntity;
import com.nlitvins.web_application.outbound.repository.jpa.BookJpaRepository;
import com.nlitvins.web_application.outbound.repository.jpa.ReservationJpaRepository;
import com.nlitvins.web_application.outbound.repository.jpa.UserJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationDetailedRepositoryImplTest {

    ReservationJpaRepository reservationMock = mock();
    BookJpaRepository bookMock = mock();
    UserJpaRepository userMock = mock();
    private ReservationDetailedRepositoryImpl sut;

    @BeforeAll
    void setup() {
        sut = new ReservationDetailedRepositoryImpl(reservationMock, bookMock, userMock);
    }

    @BeforeEach
    void clear() {
        reset(reservationMock, bookMock, userMock);
    }

    @Test
    void test() {
        doReturn(List.of(newReservation(1, 11, 111), newReservation(2, 22, 222))).when(reservationMock).findAll();
        doReturn(List.of(newBook(11), newBook(22))).when(bookMock).findByIdIn(Set.of(11, 22));
        doReturn(List.of(newUser(111), newUser(222))).when(userMock).findByIdIn(Set.of(111, 222));


        List<ReservationDetailed> result = sut.findAll();
    }

    private ReservationEntity newReservation(int id, int bookId, int userId) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(id);
        reservationEntity.setUserId(userId);
        reservationEntity.setBookId(bookId);
        reservationEntity.setStatus((short) 1);
        reservationEntity.setExtensionCount((short) 1);
        return reservationEntity;
    }

    private BookEntity newBook(int id) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(id);
        bookEntity.setTitle("Title " + id);
        bookEntity.setAuthor("Author " + id);
        bookEntity.setQuantity(1);
        bookEntity.setStatus((short) 1);
        bookEntity.setGenre((short) 1);
        bookEntity.setType((short) 1);
        return bookEntity;
    }

    private UserEntity newUser(int id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName("Jane " + id);
        userEntity.setSecondName("Smith " + id);
        userEntity.setRole((short) 1);
        return userEntity;
    }

}