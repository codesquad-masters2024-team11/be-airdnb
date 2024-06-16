package com.yourbnb.reservation.model;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import com.yourbnb.reservation.model.dto.ReservationUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 가 사용할 기본 생성자
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer visitorNumber;
    private Integer totalPrice;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;

    private Reservation(LocalDate checkInDate, LocalDate checkOutDate, Integer visitorNumber, Integer totalPrice, Member member, Accommodation accommodation) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.visitorNumber = visitorNumber;
        this.totalPrice = totalPrice;
        this.member = member;
        this.accommodation = accommodation;
    }

    public static Reservation of(LocalDate checkInDate, LocalDate checkOutDate, Integer visitorNumber, Integer totalPrice, Member member, Accommodation accommodation){
        return new Reservation(checkInDate, checkOutDate, visitorNumber, totalPrice, member, accommodation);
    }

    public Reservation update(ReservationUpdateRequest reservationUpdateRequest, int totalPrice){
        this.checkInDate = reservationUpdateRequest.checkInDate();
        this.checkOutDate = reservationUpdateRequest.checkOutDate();
        this.visitorNumber = reservationUpdateRequest.visitorNumber();
        this.totalPrice = totalPrice;

        return this;
    }
}
