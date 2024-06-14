package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.AccommodationType;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationCreateDto;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.accommodation.util.AccommodationMapper;
import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.image.service.AccommodationImageService;
import com.yourbnb.image.util.ImageMapper;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.service.MemberService;
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
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeService typeService;
    private final AccommodationImageService imageService;
    private final AccommodationAmenityService accommodationAmenityService;
    private final MemberService memberService;

    /**
     * 데이터베이스에 저장되어 있는 전체 숙소 리스트를 반환한다.
     *
     * @return 전체 숙소 리스트
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AccommodationResponse> getAccommodations() {
        List<AccommodationResponse> accommodationResponses = accommodationRepository.findAll().stream()
                .map(this::mapAccommodationToResponse)
                .toList();

        log.info("총 {}개의 숙소 탐색 성공", accommodationResponses.size());
        return accommodationResponses;
    }

    /**
     * 숙소를 데이터베이스에 저장한다.
     *
     * @param createDto               생성할 숙소 정보를 담은 DTO
     * @param accommodationAmenityIds 생성할 숙소의 어매니티들의 아이디 리스트
     * @return 생성된 숙소 응답 DTO
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AccommodationResponse createAccommodation(AccommodationCreateDto createDto,
                                                     Set<Long> accommodationAmenityIds) {
        Member member = memberService.getMemberByIdOrThrow(createDto.getHostId());
        AccommodationType type = typeService.getAccommodationTypeByIdOrThrow(createDto.getAccommodationTypeId());
        AccommodationImage image = imageService.getAccommodationImageByIdOrThrow(createDto.getAccommodationImageId());
        List<Amenity> amenities = accommodationAmenityService.getAmenitiesByIdOrThrow(accommodationAmenityIds);

        Accommodation accommodation = AccommodationMapper.toAccommodation(createDto, member, type, image);
        accommodationRepository.save(accommodation);
        accommodationAmenityService.saveAccommodationAmenity(accommodation, amenities);

        log.info("숙소 생성 성공 - {}", accommodation.getId());
        return mapAccommodationToResponse(accommodation); // 생성된 숙소를 응답 DTO로 매핑하여 반환
    }

    private AccommodationResponse mapAccommodationToResponse(Accommodation accommodation) {
        Set<Long> amenityIds = accommodationAmenityService.findAmenityIdsByAccommodationId(accommodation.getId());
        Set<Amenity> amenities = accommodationAmenityService.findAllByIdIsIn(amenityIds);
        AccommodationImageDto imageDto = ImageMapper.toAccommodationImageDto(accommodation.getAccommodationImages());
        return AccommodationMapper.toAccommodationResponse(accommodation, amenities, imageDto);
    }
}
