package com.yourbnb.review.model;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import com.yourbnb.review.model.dto.ReviewUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 가 사용할 기본 생성자
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Double rate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;

    private Review(String content, Double rate, Member member, Accommodation accommodation) {
        this.content = content;
        this.rate = rate;
        this.member = member;
        this.accommodation = accommodation;
    }

    public static Review of(String content, Double rate, Member member, Accommodation accommodation) {
        return new Review(content, rate, member, accommodation);
    }

    public Review update(ReviewUpdateRequest reviewUpdateRequest){
        this.content = reviewUpdateRequest.content();
        this.rate = reviewUpdateRequest.rate();

        return this;
    }
}
