package com.yourbnb.member.model;

import com.yourbnb.accommodation.model.Accommodation;
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
public class Member {

    @Id
    private String memberId;

    @NonNull
    private String password;

    @OneToMany
    private List<Accommodation> accommodations;

    @OneToMany
    private List<Review> reviews;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations;
}
