package com.yourbnb.image.controller;

import com.yourbnb.image.dto.AccommodationImageDto;
import com.yourbnb.image.service.AccommodationImageService;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/accommodations/images")
@RequiredArgsConstructor
public class AccommodationImageController {
    private final AccommodationImageService accommodationImageService;

    @PostMapping
    public ResponseEntity<AccommodationImageDto> uploadFile(@RequestParam MultipartFile file) throws IOException {
        AccommodationImageDto uploadImageDto = accommodationImageService.uploadAccommodationImage(file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uploadImageDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(uploadImageDto);
    }
}
