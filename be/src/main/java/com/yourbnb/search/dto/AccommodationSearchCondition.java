package com.yourbnb.search.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccommodationSearchCondition {
    private final String region;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Integer guestNumber;
}
