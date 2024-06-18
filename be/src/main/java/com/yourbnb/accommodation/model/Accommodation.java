package com.yourbnb.accommodation.model;

import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.member.model.Member;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.review.model.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED) // JPA 가 사용할 기본 생성자
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE accommodation SET is_deleted = true WHERE id = ?")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String longitude;
    private final String latitude;
    private final Integer maxCapacity;
    private final Integer cleaningFee;
    private final Integer price;
    private final String roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_type_id", referencedColumnName = "id")
    private final AccommodationType accommodationType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_image_id", referencedColumnName = "id")
    private final AccommodationImage accommodationImages;

    @OneToMany(mappedBy = "accommodations", cascade = CascadeType.ALL)
    private final Set<AccommodationAmenity> accommodationAmenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "member_id")
    private final Member host;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private final Set<Review> reviews;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private final Set<Reservation> reservations;

    private final Boolean isDeleted;
}
