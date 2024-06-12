package com.yourbnb.accommodation.util;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.AccommodationType;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationAmenityDto;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.model.dto.AccommodationTypeDto;
import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.model.AccommodationImage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccommodationMapper {
    public static AccommodationResponse toAccommodationResponse(Accommodation accommodation,
                                                                Set<Amenity> amenities,
                                                                String url) {
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .phoneNumber(accommodation.getPhoneNumber())
                .address(accommodation.getAddress())
                .longitude(accommodation.getLongitude())
                .latitude(accommodation.getLatitude())
                .maxCapacity(accommodation.getMaxCapacity())
                .cleaningFee(accommodation.getCleaningFee())
                .price(accommodation.getPrice())
                .roomType(accommodation.getRoomType())
                .hostId(accommodation.getHost().getMemberId())
                .accommodationType(toAccommodationTypeDto(accommodation.getAccommodationType()))
                .accommodationImage(toAccommodationImageDto(accommodation.getAccommodationImages(), url))
                .accommodationAmenities(toAccommodationAmenityDtos(amenities))
                .build();
    }

    public static AccommodationTypeDto toAccommodationTypeDto(AccommodationType type) {
        return new AccommodationTypeDto(type.getId(), type.getName());
    }

    public static AccommodationImageDto toAccommodationImageDto(AccommodationImage image, String imageUrl) {
        return new AccommodationImageDto(image.getId(), image.getUploadName(), imageUrl);
    }

    public static List<AccommodationAmenityDto> toAccommodationAmenityDtos(Set<Amenity> amenities) {
        return amenities.stream()
                .map(accommodationAmenity ->
                        new AccommodationAmenityDto(accommodationAmenity.getId(), accommodationAmenity.getName()))
                .collect(Collectors.toList());
    }
}
