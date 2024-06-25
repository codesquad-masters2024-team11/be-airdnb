package com.yourbnb.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yourbnb.reservation.model.QReservation;
import com.yourbnb.reservation.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reservation> findOverlappingReservations(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate) {
        QReservation qReservation = QReservation.reservation;
        return queryFactory.selectFrom(qReservation)
                .where(
                        qReservation.accommodation.id.eq(accommodationId)
                                .and(qReservation.checkInDate.lt(checkOutDate))
                                .and(qReservation.checkOutDate.gt(checkInDate))
                )
                .fetch();
    }
}
