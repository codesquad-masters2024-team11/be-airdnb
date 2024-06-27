package com.yourbnb.search.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Coordinate {
    private final double latitude;
    private final double longitude;
}
