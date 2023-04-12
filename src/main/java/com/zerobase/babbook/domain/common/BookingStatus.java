package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum BookingStatus {
    BOOKING_UN_USE("예약 중-미사용"),
    BOOKING_USE("예약 중-사용");

    private final String bookingStatus;
    BookingStatus(String bookingStatus){
        this.bookingStatus = bookingStatus;
    }
}
