package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum PartnershipStatus {
    ACCEPT("파트너쉽 승인-활성화"),
    REJECT("파트너쉽 승인-거절-잘못된 양식이거나 이미 등록된 번호입니다.");

    private final String partnershipStatus;

    PartnershipStatus(String partnershipStatus) {

        this.partnershipStatus = partnershipStatus;
    }
}

