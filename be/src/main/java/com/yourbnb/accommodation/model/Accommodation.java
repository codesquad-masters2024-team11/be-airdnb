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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 가 사용할 기본 생성자
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String longitude;
    private String latitude;
    private Integer maxCapacity;
    private Integer cleaningFee;
    private Integer price;
    private String roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_type_id", referencedColumnName = "id")
    private AccommodationType accommodationType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_image_id", referencedColumnName = "id")
    private AccommodationImage accommodationImages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accommodation_amenity",
            joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id", referencedColumnName = "id")
    )
    private Set<AccommodationAmenity> accommodationAmenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "member_id")
    private Member host;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true) // 숙소가 삭제되면 리뷰도 삭제?
    private Set<Review> reviews;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

}
