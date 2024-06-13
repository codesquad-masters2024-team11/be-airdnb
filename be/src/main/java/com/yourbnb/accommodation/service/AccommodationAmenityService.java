package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.exception.AmenityNotFoundException;
import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.AccommodationAmenity;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.repository.AccommodationAmenityRepository;
import com.yourbnb.accommodation.repository.AmenityRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationAmenityService {
    private final AmenityRepository amenityRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;

    /**
     * 데이터베이스에서 어매니티들을 조회하고 존재하면 반환한다.
     *
     * @param accommodationAmenityIds 어매니티 아이디 리스트
     * @return 어매니티 엔티티 객체 리스트
     * @throws AmenityNotFoundException 어매니티를 찾을 수 없는 경우
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Amenity> getAmenitiesByIdOrThrow(Set<Long> accommodationAmenityIds) {
        return accommodationAmenityIds.stream()
                .map(id -> amenityRepository.findById(id)
                        .orElseThrow(() -> new AmenityNotFoundException(id)))
                .toList();
    }

    /**
     * 숙소와 관련된 어메니티를 저장한다.
     *
     * @param accommodation 숙소 엔터티
     * @param amenities     저장할 어매니티 리스트
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveAccommodationAmenity(Accommodation accommodation, List<Amenity> amenities) {
        List<AccommodationAmenity> accommodationAmenities = amenities.stream()
                .map(amenity -> new AccommodationAmenity(accommodation, amenity))
                .toList();
        accommodationAmenityRepository.saveAll(accommodationAmenities);
    }

    /**
     * 특정 숙소에 연결된 어매니티들의 ID를 조회한다.
     *
     * @param id 숙소의 ID
     * @return 숙소에 연결된 어매니티들의 ID 리스트
     */
    public Set<Long> findAmenitiesByAccommodationId(Long id) {
        return accommodationAmenityRepository.findAmenitiesByAccommodationId(id);
    }

    /**
     * 전달받은 ID들에 해당하는 어매니티들을 조회한다.
     *
     * @param amenityIds 검색할 어매니티들의 ID 리스트
     * @return 어매니티 엔티티 객체들의 리스트
     */
    public Set<Amenity> findAllByIdIsIn(Set<Long> amenityIds) {
        return amenityRepository.findAllByIdIsIn(amenityIds);
    }
}
