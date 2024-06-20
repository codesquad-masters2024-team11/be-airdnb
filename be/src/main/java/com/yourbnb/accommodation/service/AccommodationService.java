package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.exception.AccommodationNotFoundException;
import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.AccommodationType;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationCreateDto;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.model.dto.AccommodationUpdateDto;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.accommodation.util.AccommodationMapper;
import com.yourbnb.global.exception.ResourceAccessDeniedException;
import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.image.service.AccommodationImageService;
import com.yourbnb.image.util.ImageMapper;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.service.MemberService;
import com.yourbnb.search.dto.AccommodationSearchCondition;
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

    /**
     * 호스트의 숙소 중 특정 ID의 숙소를 조회한다.
     *
     * @param id     조회할 숙소의 ID
     * @param hostId 조회를 요청한 호스트의 ID
     * @return 조회된 숙소 객체
     * @throws AccommodationNotFoundException 요청한 ID에 해당하는 숙소가 없을 경우
     * @throws ResourceAccessDeniedException  다른 호스트의 숙소를 수정/삭제하려고 할 경우
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Accommodation getAccommodationByIdForHost(Long id, String hostId) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationNotFoundException(id));

        if (!accommodation.getHost().getMemberId().equals(hostId)) {
            throw new ResourceAccessDeniedException("다른 호스트의 숙소는 수정/삭제할 수 없습니다.");
        }
        return accommodation;
    }

    /**
     * 숙소 정보를 업데이트한다.
     *
     * @param accommodation 업데이트할 기존 숙소 객체
     * @param updateDto     업데이트할 새로운 정보가 담긴 DTO 객체
     * @return 업데이트된 숙소 응답 DTO
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AccommodationResponse updateAccommodation(Accommodation accommodation, AccommodationUpdateDto updateDto) {
        AccommodationType type = getAccommodationType(updateDto);
        AccommodationImage image = getAccommodationImage(updateDto);
        Accommodation newAccommodation = AccommodationMapper.toNewAccommodation(accommodation, updateDto, type, image);
        Accommodation updatedAccommodation = accommodationRepository.save(newAccommodation);

        Set<Long> accommodationAmenityIds = updateDto.getAccommodationAmenityIds();
        if (accommodationAmenityIds != null && !accommodationAmenityIds.isEmpty()) {
            accommodationAmenityService.updateAccommodationAmenity(updatedAccommodation, accommodationAmenityIds);
        }
        return mapAccommodationToResponse(updatedAccommodation);
    }

    /**
     * 주어진 ID에 해당하는 숙소를 삭제한다.
     *
     * @param id 삭제할 숙소의 ID
     * @throws AccommodationNotFoundException 요청한 ID에 해당하는 숙소가 없을 경우
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAccommodation(Long id) {
        try {
            accommodationRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new AccommodationNotFoundException(id);
        }
    }

    /**
     * 주어진 검색 조건에 따라 숙소를 검색한다.
     *
     * @param condition 검색 조건을 담고 있는 객체
     * @return 검색된 숙소 목록을 AccommodationResponse 객체로 변환한 리스트
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AccommodationResponse> getSearchedAccommodations(AccommodationSearchCondition condition) {
        return accommodationRepository.findAccommodationsByCriteria(condition).stream()
                .map(this::mapAccommodationToResponse)
                .toList();
    }

    private AccommodationImage getAccommodationImage(AccommodationUpdateDto updateDto) {
        if (updateDto.getAccommodationImageId() != null) {
            return imageService.getAccommodationImageByIdOrThrow(updateDto.getAccommodationImageId());
        }
        return null;
    }

    private AccommodationType getAccommodationType(AccommodationUpdateDto updateDto) {
        if (updateDto.getAccommodationTypeId() != null) {
            return typeService.getAccommodationTypeByIdOrThrow(updateDto.getAccommodationTypeId());
        }
        return null;
    }

    private AccommodationResponse mapAccommodationToResponse(Accommodation accommodation) {
        Set<Long> amenityIds = accommodationAmenityService.findAmenityIdsByAccommodationId(accommodation.getId());
        Set<Amenity> amenities = accommodationAmenityService.findAllByIdIsIn(amenityIds);
        AccommodationImageDto imageDto = ImageMapper.toAccommodationImageDto(accommodation.getAccommodationImages());
        return AccommodationMapper.toAccommodationResponse(accommodation, amenities, imageDto);
    }
}
