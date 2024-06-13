package com.yourbnb.review.model.dto;

import com.yourbnb.review.model.Review;
import lombok.Getter;

@Getter
public class ReviewUpdateResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "리뷰 수정 성공! 리뷰 #%d 내용 : %s";

    private final Long id;
    private final String content;
    private final String message;

    public ReviewUpdateResponse(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.message = generateSuccessMessage(review);
    }

    private String generateSuccessMessage(Review review) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, review.getId(), review.getContent());
    }

    public static ReviewUpdateResponse fromm (Review review){
        return new ReviewUpdateResponse(review);
    }


}
