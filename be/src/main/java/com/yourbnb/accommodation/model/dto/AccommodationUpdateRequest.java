package com.yourbnb.accommodation.model.dto;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class AccommodationUpdateRequest {
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String longitude;
    private final String latitude;
    private final Integer maxCapacity;
    private final Integer cleaningFee;
    private final Integer price;
    private final String roomType;
    private final String hostId; // TODO: 로그인 구현시 삭제 예정
    private final Long accommodationTypeId;
    private final Long accommodationImageId;
    private final Set<Long> accommodationAmenityIds;
}
