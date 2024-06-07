package com.yourbnb.review.model;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // JPA 가 사용할 기본 생성자
@RequiredArgsConstructor // @NotNull 로 표시된 필드를 초기화하는 생성자
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String comment;

    @NonNull
    private Double rate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Accommodation accommodation;
}
