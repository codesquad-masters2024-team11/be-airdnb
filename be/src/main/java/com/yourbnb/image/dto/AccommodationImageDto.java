package com.yourbnb.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class AccommodationImageDto {
    private final Long id;
    private final String uploadName;
    private final String imageUrl;
}
