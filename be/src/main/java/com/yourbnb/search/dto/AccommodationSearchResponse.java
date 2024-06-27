package com.yourbnb.search.dto;

import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccommodationSearchResponse {
    private final Coordinate coordinate;
    private final List<AccommodationResponse> accommodations;
}
