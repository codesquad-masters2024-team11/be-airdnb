package com.yourbnb.reservation.repository;

import com.yourbnb.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends ReservationRepositoryCustom, JpaRepository<Reservation, Long> {
    List<Reservation> findByAccommodationId(Long accommodationId);
    // Spring Data JPA는 기본적으로 'id'라는 이름을 기대, 하지만 Member 엔티티의 필드 이름은 memberId.
    List<Reservation> findByMember_MemberId(String memberId);
    List<Reservation> findOverlappingReservations(Long aLong, LocalDate localDate, LocalDate localDate1);
}
