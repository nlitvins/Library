package com.nlitvins.web_application.outbound.repository;

import com.nlitvins.web_application.domain.model.Book;
import com.nlitvins.web_application.domain.model.ReservationDetailed;
import com.nlitvins.web_application.domain.model.User;
import com.nlitvins.web_application.domain.repository.ReservationDetailedRepository;
import com.nlitvins.web_application.outbound.model.BookEntity;
import com.nlitvins.web_application.outbound.model.ReservationEntity;
import com.nlitvins.web_application.outbound.model.UserEntity;
import com.nlitvins.web_application.outbound.repository.jpa.BookJpaRepository;
import com.nlitvins.web_application.outbound.repository.jpa.ReservationJpaRepository;
import com.nlitvins.web_application.outbound.repository.jpa.UserJpaRepository;
import com.nlitvins.web_application.outbound.utils.OutboundMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ReservationDetailedRepositoryImpl implements ReservationDetailedRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final BookJpaRepository bookJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public ReservationDetailedRepositoryImpl(ReservationJpaRepository reservationJpaRepository, BookJpaRepository bookJpaRepository, UserJpaRepository userJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<ReservationDetailed> findAll() {
        List<ReservationEntity> reservationEntities = reservationJpaRepository.findAll();
        Set<Integer> bookIds = new HashSet<>();
        Set<Integer> userIds = new HashSet<>();

        for (ReservationEntity reservationEntity : reservationEntities) {
            bookIds.add(reservationEntity.getBookId());
            userIds.add(reservationEntity.getUserId());
        }

        Map<Integer, Book> bookMap = getBookMap(bookIds);
        Map<Integer, User> userMap = getUserMap(userIds);

        List<ReservationDetailed> reservationDetailedList = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities) {
            reservationDetailedList.add(
                    ReservationDetailed.builder()
                            .reservation(OutboundMapper.Reservations.toDomain(reservationEntity))
                            .user(userMap.get(reservationEntity.getUserId()))
                            .book(bookMap.get(reservationEntity.getBookId()))
                            .build()
            );
        }

        return reservationDetailedList;
    }

    private Map<Integer, Book> getBookMap(Set<Integer> bookIds) {
        List<BookEntity> bookEntities = bookJpaRepository.findByIdIn(bookIds);
        Map<Integer, Book> bookMap = new HashMap<>();
        for (BookEntity bookEntity : bookEntities) {
            bookMap.put(bookEntity.getId(), OutboundMapper.Books.toDomain(bookEntity));
        }
        return bookMap;
    }

    private Map<Integer, User> getUserMap(Set<Integer> usersIds) {
        List<UserEntity> userEntities = userJpaRepository.findByIdIn(usersIds);
        Map<Integer, User> userMap = new HashMap<>();
        for (UserEntity userEntity : userEntities) {
            userMap.put(userEntity.getId(), OutboundMapper.Users.toDomain(userEntity));
        }
        return userMap;
    }
}
