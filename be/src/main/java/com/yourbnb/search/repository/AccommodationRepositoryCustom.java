package com.yourbnb.search.repository;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.search.dto.AccommodationSearchCondition;
import java.util.List;

public interface AccommodationRepositoryCustom {
    List<Accommodation> findAccommodationsByCriteria(AccommodationSearchCondition condition);
}
