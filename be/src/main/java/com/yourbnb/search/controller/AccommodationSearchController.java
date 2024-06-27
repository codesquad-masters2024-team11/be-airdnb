package com.yourbnb.search.controller;

import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.service.AccommodationService;
import com.yourbnb.search.dto.AccommodationSearchCondition;
import com.yourbnb.search.dto.AccommodationSearchResponse;
import com.yourbnb.search.dto.Coordinate;
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
    public AccommodationSearchResponse getSearchedAccommodations(AccommodationSearchCondition condition) {
        List<AccommodationResponse> searchedAccommodations = accommodationService.getSearchedAccommodations(condition);
        Coordinate coordinate = accommodationService.getCoordinate(searchedAccommodations);
        return new AccommodationSearchResponse(coordinate, searchedAccommodations);
    }
}
