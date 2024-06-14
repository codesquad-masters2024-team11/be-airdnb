package com.yourbnb.accommodation.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private final Integer maxCapacity;
    @NotNull
    private final Integer cleaningFee;
    @NotNull
    private final Integer price;
    @NotBlank
    private final String roomType;
    @NotBlank
    private final String hostId;
    @NotNull
    private final Long accommodationTypeId;
    @NotNull
    private final Long accommodationImageId;
    private final Set<Long> accommodationAmenityIds;
}
