package com.zerobase.babbook.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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
