package com.yourbnb.reservation.model;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // JPA 가 사용할 기본 생성자
@RequiredArgsConstructor // @NotNull 로 표시된 필드를 초기화하는 생성자
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private LocalDate checkInDate;

    @NonNull
    private LocalDate checkOutDate;

    @NonNull
    private Integer visitorNumber;

    @NonNull
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    private List<Accommodation> accommodations;
}
