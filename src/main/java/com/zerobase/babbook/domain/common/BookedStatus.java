package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum BookedStatus {
    BOOKED_USED("예약 끝-사용 완료"),
    BOOKED_NO_USE("예약 끝-미사용");

    private final String bookedStatus;
    BookedStatus(String commonStatus){
        this.bookedStatus = commonStatus;
    }
}

