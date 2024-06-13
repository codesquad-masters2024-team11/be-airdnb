package com.yourbnb.review.model.dto;

import lombok.Getter;

@Getter
public class ReviewDeleteResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "리뷰 삭제 성공! 리뷰 #%d";

    private final Long id;
    private final String message;

    public ReviewDeleteResponse(Long id) {
        this.id = id;
        this.message = generateSuccessMessage(id);
    }

    private static String generateSuccessMessage(Long id) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, id);
    }

    public static ReviewDeleteResponse from(Long id) {
        return new ReviewDeleteResponse(id);
    }
}
