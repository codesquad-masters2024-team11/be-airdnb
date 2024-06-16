package com.yourbnb.reservation.model.dto;

import java.time.LocalDate;

public record ReservationUpdateRequest(LocalDate checkInDate,
                                       LocalDate checkOutDate,
                                       Integer visitorNumber) {
}
