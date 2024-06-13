package com.yourbnb.accommodation.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class AccommodationCreateRequest {
    @NotBlank
    private final String name;
    @NotBlank
    private final String phoneNumber;
    @NotBlank
    private final String address;
    @NotBlank
    private final String longitude;
    @NotBlank
    private final String latitude;
    @NotBlank
    private final Integer maxCapacity;
    @NotBlank
    private final Integer cleaningFee;
    @NotBlank
    private final Integer price;
    @NotBlank
    private final String roomType;
    @NotBlank
    private final String hostId;
    @NotBlank
    private final Long accommodationTypeId;
    @NotBlank
    private final Long accommodationImageId;
    private final Set<Long> accommodationAmenityIds;
}
