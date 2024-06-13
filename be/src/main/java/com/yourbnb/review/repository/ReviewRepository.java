package com.yourbnb.review.repository;

import com.yourbnb.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByAccommodationId(Long accommodationId);
    Optional<Review> findByIdAndAccommodationId(Long id, Long accommodationId);
}
