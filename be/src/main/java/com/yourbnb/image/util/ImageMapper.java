package com.yourbnb.image.util;

import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.model.AccommodationImage;

public class ImageMapper {
    public static AccommodationImageDto toAccommodationImageDto(AccommodationImage savedImage, String imageUrl) {
        return new AccommodationImageDto(savedImage.getId(), savedImage.getUploadName(), imageUrl);
    }
}
