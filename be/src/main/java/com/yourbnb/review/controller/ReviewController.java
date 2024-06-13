package com.yourbnb.review.controller;

import com.yourbnb.review.model.Review;
import com.yourbnb.review.model.dto.*;
import com.yourbnb.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewCreationResponse> createReview(@RequestBody ReviewCreationRequest request) {
        Review createdReview = reviewService.createReview(request);

        ReviewCreationResponse response = ReviewCreationResponse.from(createdReview);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByAccommodationId(@PathVariable Long accommodationId){
        List<ReviewResponse> reviews = reviewService.getReviewsByAccommodationId(accommodationId);
        return ResponseEntity.ok(reviews);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        Review updatedReview = reviewService.updateReview(reviewId, reviewUpdateRequest);

        ReviewUpdateResponse response = ReviewUpdateResponse.fromm(updatedReview);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);

        ReviewDeleteResponse response = ReviewDeleteResponse.from(reviewId);

        return ResponseEntity.ok(response);
    }
}
