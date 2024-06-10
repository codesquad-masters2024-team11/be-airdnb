package com.yourbnb.member.model;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.review.model.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 가 사용할 기본 생성자
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;
    private String password;
    @OneToMany(mappedBy = "host")
    private Set<Accommodation> accommodations;
    @OneToMany(mappedBy = "member")
    private Set<Review> reviews;
    @OneToMany(mappedBy = "member")
    private Set<Reservation> reservations;
}
