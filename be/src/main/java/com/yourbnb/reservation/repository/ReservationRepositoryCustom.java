package com.yourbnb.reservation.repository;

import com.yourbnb.reservation.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
    List<Reservation> findOverlappingReservations(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate);
}
