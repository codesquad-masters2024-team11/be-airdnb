package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.exception.AccommodationTypeNotFoundException;
import com.yourbnb.accommodation.model.AccommodationType;
import com.yourbnb.accommodation.repository.AccommodationTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;

    /**
     * 데이터베이스에서 숙소 타입을 조회하고 존재하면 반환한다.
     *
     * @param id 숙소 타입 아이디
     * @return 숙소 타입 엔티티 객체
     * @throws AccommodationTypeNotFoundException 숙소 타입을 찾을 수 없는 경우
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public AccommodationType getAccommodationTypeByIdOrThrow(Long id) {
        return accommodationTypeRepository.findById(id).orElseThrow(() -> new AccommodationTypeNotFoundException(id));
    }
}
