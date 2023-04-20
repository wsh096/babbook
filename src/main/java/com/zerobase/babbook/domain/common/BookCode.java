package com.zerobase.babbook.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookCode implements EnumMapperType{
    CAN_BOOK("예약 가능"),
    IN_BOOK("예약 중"),
    CANT_BOOK("예약 불가능");

    private String bookCode;

    BookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getCode() {
        return this.bookCode;
    }

}
