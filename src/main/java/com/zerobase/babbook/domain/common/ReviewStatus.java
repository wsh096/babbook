package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    CAN_REVIEW("리뷰 가능"),//boolean 대체 가능 이 상태면.
    CANT_BOOK("리뷰 불가능");

    private final String reviewStatus;

    ReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

}
