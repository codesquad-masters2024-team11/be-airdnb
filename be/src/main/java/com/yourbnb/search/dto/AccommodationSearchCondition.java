package com.yourbnb.search.dto;

import com.yourbnb.search.exception.InvalidCheckInCheckOutDateException;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AccommodationSearchCondition {
    private final String region;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Integer guestNumber;

    public AccommodationSearchCondition(String region,
                                        LocalDate checkInDate,
                                        LocalDate checkOutDate,
                                        Integer guestNumber) {
        if (!checkInDate.isBefore(checkOutDate)) {
            throw new InvalidCheckInCheckOutDateException();
        }
        this.region = region;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestNumber = guestNumber;
    }
}
