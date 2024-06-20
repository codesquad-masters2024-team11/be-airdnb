package com.yourbnb.search.controller;

import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.service.AccommodationService;
import com.yourbnb.search.dto.AccommodationSearchCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class AccommodationSearchController {
    private final AccommodationService accommodationService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<AccommodationResponse> getSearchedAccommodations(AccommodationSearchCondition condition) {
        // TODO: 체크인 체크아웃 날짜 서로 범위 맞는지 확인
        return accommodationService.getSearchedAccommodations(condition);
    }
}
