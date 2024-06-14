package com.yourbnb.accommodation.util;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.AccommodationType;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationAmenityDto;
import com.yourbnb.accommodation.model.dto.AccommodationCreateDto;
import com.yourbnb.accommodation.model.dto.AccommodationCreateRequest;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.model.dto.AccommodationTypeDto;
import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.member.model.Member;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccommodationMapper {
    public static AccommodationResponse toAccommodationResponse(Accommodation accommodation,
                                                                Set<Amenity> amenities,
                                                                AccommodationImageDto accommodationImageDto) {
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
                .accommodationImage(accommodationImageDto)
                .accommodationAmenities(toAccommodationAmenityDtos(amenities))
                .build();
    }

    public static AccommodationTypeDto toAccommodationTypeDto(AccommodationType type) {
        return new AccommodationTypeDto(type.getId(), type.getName());
    }

    public static List<AccommodationAmenityDto> toAccommodationAmenityDtos(Set<Amenity> amenities) {
        return amenities.stream()
                .map(accommodationAmenity ->
                        new AccommodationAmenityDto(accommodationAmenity.getId(), accommodationAmenity.getName()))
                .collect(Collectors.toList());
    }

    public static AccommodationCreateDto toAccommodationCreateDto(AccommodationCreateRequest request) {

        return AccommodationCreateDto.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .maxCapacity(request.getMaxCapacity())
                .cleaningFee(request.getCleaningFee())
                .price(request.getPrice())
                .roomType(request.getRoomType())
                .hostId(request.getHostId())
                .accommodationTypeId(request.getAccommodationTypeId())
                .accommodationImageId(request.getAccommodationImageId())
                .build();
    }

    public static Accommodation toAccommodation(AccommodationCreateDto createDto,
                                                Member member,
                                                AccommodationType accommodationType,
                                                AccommodationImage accommodationImage) {

        return Accommodation.builder()
                .name(createDto.getName())
                .phoneNumber(createDto.getPhoneNumber())
                .address(createDto.getAddress())
                .longitude(createDto.getLongitude())
                .latitude(createDto.getLatitude())
                .maxCapacity(createDto.getMaxCapacity())
                .cleaningFee(createDto.getCleaningFee())
                .price(createDto.getPrice())
                .roomType(createDto.getRoomType())
                .host(member)
                .accommodationType(accommodationType)
                .accommodationImages(accommodationImage)
                .build();
    }
}
