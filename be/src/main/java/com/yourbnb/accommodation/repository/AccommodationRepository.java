package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
