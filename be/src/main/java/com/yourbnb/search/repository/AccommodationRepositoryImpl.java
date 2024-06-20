package com.yourbnb.search.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.QAccommodation;
import com.yourbnb.reservation.model.QReservation;
import com.yourbnb.search.dto.AccommodationSearchCondition;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Accommodation> findAccommodationsByCriteria(AccommodationSearchCondition condition) {
        final QAccommodation qAccommodation = QAccommodation.accommodation;
        JPQLQuery<Long> subQuery = getSubQuery(condition.getCheckInDate(), condition.getCheckOutDate());

        return queryFactory.selectFrom(qAccommodation)
                .where(eqRegion(condition.getRegion()),
                        goeGuestNum(condition.getGuestNumber()),
                        qAccommodation.id.notIn(subQuery))
                .fetch();
    }

    private BooleanExpression eqRegion(String region) {
        QAccommodation qAccommodation = QAccommodation.accommodation;
        if (StringUtils.isEmpty(region)) {
            return qAccommodation.id.isNotNull();
        }
        return qAccommodation.address.likeIgnoreCase("%" + region + "%");
    }

    private BooleanExpression goeGuestNum(Integer guestNum) {
        QAccommodation qAccommodation = QAccommodation.accommodation;
        if (guestNum == null) {
            return qAccommodation.id.isNotNull();
        }
        return qAccommodation.maxCapacity.goe(guestNum);
    }

    private JPQLQuery<Long> getSubQuery(LocalDate checkInDate, LocalDate checkOutDate) {
        final QReservation qReservation = QReservation.reservation;
        return queryFactory.select(qReservation.accommodation.id)
                .from(qReservation)
                .where(qReservation.checkInDate.goe(checkInDate)
                        .and(qReservation.checkOutDate.loe(checkOutDate))
                        .or(qReservation.checkInDate.lt(checkOutDate))
                        .or(qReservation.checkOutDate.gt(checkInDate))
                );
    }
}
