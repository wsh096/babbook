package com.zerobase.babbook.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
/**
* 승인과 거절 두 가지의 값을 가지고,
* 유저가 요청한 예약에 관해
* 점주가 적절하게 응답함에 따라
* StatusCode를 바꿔주는 역할.
* */
@Getter
public enum OwnerAdmit implements EnumMapperType {
    ACCEPT("승인"),//boolean 대체 가능 이 상태면.
    REJECT("거절");//점주가 예약을 승인할지 말지에 대한 판단을 위한 Enum
    private final String status;

    OwnerAdmit(String status) {
        this.status = status;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.status;
    }
}
