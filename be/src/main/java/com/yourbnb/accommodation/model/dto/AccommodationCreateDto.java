package com.yourbnb.accommodation.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AccommodationCreateDto {
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String longitude;
    private final String latitude;
    private final Integer maxCapacity;
    private final Integer cleaningFee;
    private final Integer price;
    private final String roomType;
    private final String hostId;
    private final Long accommodationTypeId;
    private final Long accommodationImageId;
}
