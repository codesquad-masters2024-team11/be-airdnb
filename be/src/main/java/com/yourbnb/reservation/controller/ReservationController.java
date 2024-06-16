package com.yourbnb.reservation.controller;

import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.reservation.model.dto.ReservationCreationRequest;
import com.yourbnb.reservation.model.dto.ReservationCreationResponse;
import com.yourbnb.reservation.model.dto.ReservationResponse;
import com.yourbnb.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long reservationId) {
        Optional<ReservationResponse> reservation = reservationService.getReservationById(reservationId);

        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // TODO : memberId 에 따른 List
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByMemberId(@PathVariable String memberId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByMemberId(memberId);

        return ResponseEntity.ok(reservations);
    }


    // TODO : accommodationId 에 따른 List
    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByAccommodationId(@PathVariable Long accommodationId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByAccommodationId(accommodationId);

        return ResponseEntity.ok(reservations);
    }
}
