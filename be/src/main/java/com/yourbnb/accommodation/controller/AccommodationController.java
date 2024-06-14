package com.yourbnb.accommodation.controller;

import com.yourbnb.accommodation.model.dto.AccommodationCreateDto;
import com.yourbnb.accommodation.model.dto.AccommodationCreateRequest;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.service.AccommodationService;
import com.yourbnb.accommodation.util.AccommodationMapper;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
@Slf4j
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccommodationResponse> getAccommodations() {
        return accommodationService.getAccommodations();
    }

    @PostMapping
    public ResponseEntity<AccommodationResponse> createAccommodations(
            @Valid @RequestBody AccommodationCreateRequest request) {
        AccommodationCreateDto createDto = AccommodationMapper.toAccommodationCreateDto(request);
        AccommodationResponse accommodation = accommodationService.createAccommodation(createDto,
                request.getAccommodationAmenityIds());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accommodation.getId())
                .toUri();
        return ResponseEntity.created(location).body(accommodation);
    }
}
