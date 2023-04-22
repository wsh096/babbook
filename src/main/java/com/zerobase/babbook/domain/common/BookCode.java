package com.zerobase.babbook.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookCode implements EnumMapperType {

    USER_WAIT_BOOK("예약 진행중"),
    OWNER_ACCEPT_BOOK("점주 예약 승인"),
    OWNER_REJECT_BOOK("점주 예약 거절"),
    USER_CANCEL_BOOK("유저 예약 취소"),
    AUTO_CANCEL_BOOK("예약 자동 취소"),
    TIMEOUT_CANCEL_BOOK("시간 초과 취소");

    private String bookCode;

    BookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.bookCode;
    }

}
