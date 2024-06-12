package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.repository.AccommodationAmenityRepository;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.accommodation.repository.AmenityRepository;
import com.yourbnb.accommodation.util.AccommodationMapper;
import com.yourbnb.image.util.S3Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationService {
    private final S3Service s3Service;
    private final AccommodationRepository accommodationRepository;
    private final AmenityRepository amenityRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;


    /**
     * 데이터베이스에 저장되어 있는 전체 숙소 리스트를 반환한다.
     *
     * @return 전체 숙소 리스트
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AccommodationResponse> getAccommodations() {
        List<AccommodationResponse> accommodationResponses = accommodationRepository.findAll().stream()
                .map(this::mapAccommodationToResponse)
                .collect(Collectors.toList());

        log.info("총 {}개의 숙소 탐색 성공", accommodationResponses.size());
        return accommodationResponses;
    }

    private AccommodationResponse mapAccommodationToResponse(Accommodation accommodation) {
        Set<Long> amenityIds = accommodationAmenityRepository.findAmenitiesByAccommodationId(accommodation.getId());
        Set<Amenity> amenities = amenityRepository.findAllByIdIsIn(amenityIds);
        String url = s3Service.getImageUrl(accommodation.getAccommodationImages().getUploadName());
        return AccommodationMapper.toAccommodationResponse(accommodation, amenities, url);
    }
}
