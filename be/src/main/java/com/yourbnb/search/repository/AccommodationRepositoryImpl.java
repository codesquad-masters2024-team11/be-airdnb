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
        return qAccommodation.address.likeIgnoreCase(region + "%");
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
                .where((qReservation.checkInDate.lt(checkOutDate)) // db 체크인 2023-07-16 < 입력 체크아웃 2023-07-15
                        .and(qReservation.checkOutDate.gt(checkInDate)) // db 체크아웃 2023-07-20 > 입력 체크인 2023-07-10
                );
    }
}

// db 체크인 (16일) =< 입력 체크인 (16일) &&
// db 체크아웃(20일) >= 입력 체크아웃(20일)

// or db 체크아웃 (20일) 체크인 날짜가 사이
// db 체크인 (16일) 입력 체크아웃 (19일) 입력 체크인 (17일)
