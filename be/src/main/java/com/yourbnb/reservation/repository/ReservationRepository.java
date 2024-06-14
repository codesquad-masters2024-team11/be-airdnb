package com.yourbnb.reservation.repository;

import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long reservationId);
    List<Reservation> findByAccommodationId(Long accommodationId);
    List<Reservation> findByMemberId(String memberId);
}
