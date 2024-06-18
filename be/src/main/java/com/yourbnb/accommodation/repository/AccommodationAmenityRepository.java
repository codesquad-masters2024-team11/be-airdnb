package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.AccommodationAmenity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity, Long> {
    Set<AmenityIdMapping> findAllByAccommodations_Id(Long accommodationId);

    void deleteAllByAccommodations_Id(Long accommodationId);
}
