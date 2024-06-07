package com.yourbnb.accommodation.model;

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
public class AccommodationAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @ManyToMany
    private List<Accommodation> accommodations;
}
