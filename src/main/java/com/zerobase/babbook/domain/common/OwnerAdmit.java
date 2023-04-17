package com.zerobase.babbook.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum OwnerAdmit {
    ACCEPT("승인"),
    REJECT("거절");//점주가 예약을 승인할지 말지에 대한 판단을 위한 Enum
    private final String status;

    OwnerAdmit(String status) {
        this.status = status;
    }
}
