package com.yourbnb.accommodation.model;

import com.yourbnb.image.model.AccommodationImage;
import com.yourbnb.member.model.Member;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.review.model.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor // JPA 가 사용할 기본 생성자
@RequiredArgsConstructor // @NotNull 로 표시된 필드를 초기화하는 생성자
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String address;

    @NonNull
    private String longitude;

    @NonNull
    private String latitude;

    @NonNull
    private Integer maxCapacity;

    @NonNull
    private Integer cleaningFee;

    @NonNull
    private Integer price;

    @NonNull
    private String roomType;

    @ManyToOne // accommodation 이 many
    private AccommodationType accommodationType;

    @OneToOne
    private AccommodationImage accommodationImage;

    @ManyToMany
    private List<AccommodationAmenity> accommodationAmenity;

    @ManyToOne
    private Member host;

    @OneToMany
    private List<Review> reviews;

    @ManyToOne
    private Reservation reservation;

}
