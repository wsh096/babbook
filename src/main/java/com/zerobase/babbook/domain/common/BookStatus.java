package com.zerobase.babbook.domain.common;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum BookStatus {
    CAN_BOOK("예약 가능"),
    IN_BOOK("예약 중"),
    CANT_BOOK("예약 불가능");

    private final String bookStatus;
    BookStatus(String bookStatus){
        this.bookStatus = bookStatus;
    }

}
