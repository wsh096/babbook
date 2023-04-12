package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    CAN_REVIEW("리뷰 가능"),
    CANT_BOOK("리뷰 불가능");

    private final String reviewStatus;
    ReviewStatus(String reviewStatus){
        this.reviewStatus = reviewStatus;
    }

}
