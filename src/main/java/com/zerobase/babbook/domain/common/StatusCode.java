package com.zerobase.babbook.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 가장 핵심이 되는 enum.
 * 해당 값을 review 등으로 나눌 수도 있지만,
 * 하나로 통합해 관리.
 * 예약의 진행 상황과 review 가능 여부 등이 담겨 있음.
 */
@Getter
@RequiredArgsConstructor
public enum StatusCode implements EnumMapperType {

    USER_WAIT_BOOK("예약 진행중"),
    OWNER_ACCEPT_BOOK("예약중"),//"점주 예약 승인"
    OWNER_REJECT_BOOK("점주 예약 거절"),
    USER_CANCEL_BOOK("유저 예약 취소"),
    AUTO_CANCEL_BOOK("예약 자동 취소"),
    TIMEOUT_CANCEL_BOOK("시간 초과 취소"),
    USED_BOOK("사용 완료된 예약"),
    COMPLETE_REVIEW("리뷰 작성 완료"),
    REVIEW_WRITE_OVER("리뷰 작성 가능일 초과");

    private String statusCode;

    StatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.statusCode;
    }

}
