package com.yourbnb.reservation.controller;

import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.reservation.model.dto.ReservationCreationRequest;
import com.yourbnb.reservation.model.dto.ReservationCreationResponse;
import com.yourbnb.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationCreationResponse> createReservation(@RequestBody ReservationCreationRequest request) {
        Reservation createdReservation = reservationService.createReservation(request);

        ReservationCreationResponse response = ReservationCreationResponse.from(createdReservation);

        return ResponseEntity.ok(response);
    }

    // TODO : reservationId 개인
    // TODO : memberId 에 따른 List
    // TODO : accommodationId 에 따른 List
}
