package com.nlitvins.web_application;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<ReservationEntity> reservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return Mapper.reservationToList(reservationEntities);
    }

//    @PostMapping
//    public ReservationResponse reserveBook(@RequestBody ReservationRequest request) {
//        ReservationEntity reservationEntity = new ReservationEntity();
//
//        reservationEntity.setId(request.getId());
//        reservationEntity.setUserId(request.getUserId());
//        reservationEntity.setBookId(request.getBookId());
//        reservationEntity.setCreatedDate(LocalDateTime.now());
//        reservationEntity.setTermDate(LocalDateTime.now().plusDays(3));
//        reservationEntity.setStatus(request.getStatus());
//        reservationEntity.setExtensionCount(request.getExtensionCount());
//        reservationEntity.setUpdatedDate(request.getUpdatedDate());
//
//        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
//
//        return new ReservationResponse(
//                savedReservationEntity.getUserId(),
//                savedReservationEntity.getBookId(),
//                savedReservationEntity.getCreatedDate(),
//                savedReservationEntity.getTermDate()
//       );
//    }
//
//    @PostMapping
//    public ReservationResponse bookReceiving(@RequestBody ReservationRequest request){
//       ReservationEntity reservationEntity = new ReservationEntity();
//
//
//        reservationEntity.setId(request.getId());
//        reservationEntity.setUserId(request.getUserId());
//        reservationEntity.setBookId(request.getBookId());
//        reservationEntity.setCreatedDate(LocalDateTime.now());
//        reservationEntity.setTermDate(LocalDateTime.now().plusDays(14));
//        reservationEntity.setStatus(request.getStatus());
//        reservationEntity.setExtensionCount(request.getExtensionCount());
//        reservationEntity.setUpdatedDate(request.getUpdatedDate());
//        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);
//
//        return new ReservationResponse(
//                savedReservationEntity.getUserId(),
//                savedReservationEntity.getBookId(),
//                savedReservationEntity.getCreatedDate(),
//                savedReservationEntity.getTermDate()
//        );
//    }

    @PostMapping("/{reserveBook}")
    public ReservationResponse reserveBook(@RequestBody ReservationRequest request) {
        return saveReservation(request, 3); // срок брони 3 дня
    }

    @PostMapping("/{bookReceiving}")
    public ReservationResponse bookReceiving(@RequestBody ReservationRequest request) {
        return saveReservation(request, 14); // срок брони 14 дней
    }

    private ReservationResponse saveReservation(ReservationRequest request, int termDays) {
        ReservationEntity reservationEntity = new ReservationEntity();

        reservationEntity.setId(request.getId());
        reservationEntity.setUserId(request.getUserId());
        reservationEntity.setBookId(request.getBookId());
        reservationEntity.setCreatedDate(LocalDateTime.now());
        reservationEntity.setTermDate(LocalDateTime.now().plusDays(termDays)); // Разный срок
        reservationEntity.setStatus(request.getStatus());
        reservationEntity.setExtensionCount(request.getExtensionCount());
        reservationEntity.setUpdatedDate(request.getUpdatedDate());

        ReservationEntity savedReservationEntity = reservationRepository.save(reservationEntity);

        return new ReservationResponse(
                savedReservationEntity.getUserId(),
                savedReservationEntity.getBookId(),
                savedReservationEntity.getCreatedDate(),
                savedReservationEntity.getTermDate()
        );
    }


    @DeleteMapping
    public ResponseEntity<Void> reservationCanceling(@PathVariable int reservationId) {
        reservationRepository.deleteById(reservationId);
        return ResponseEntity.noContent().build();
    }
}
