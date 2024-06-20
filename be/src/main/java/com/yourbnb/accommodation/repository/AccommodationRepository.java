package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.search.repository.AccommodationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends AccommodationRepositoryCustom, JpaRepository<Accommodation, Long> {
}
