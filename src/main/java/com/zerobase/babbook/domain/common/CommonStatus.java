package com.zerobase.babbook.domain.common;

public enum CommonStatus {
    UN_USE,//아직 미사용한 예약
    USE, //예약이 시행되어 사용 중
    USED,//이미 사용한 예약 //해당 상태를 통해 리뷰 가능 여부 확인
    NO_USE//예약시간이 지나 사용할 수 없는 상태.
}
