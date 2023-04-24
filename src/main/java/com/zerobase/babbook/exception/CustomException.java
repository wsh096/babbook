package com.zerobase.babbook.exception;

import lombok.Getter;

/**
 * CustomException 을 처리하는 클래스
 * RuntimeException 을 상속받아 구현
 * 생성자로 ErrorCode 객체를 매개변수로 받아 예외 객체를 생성,
 * 이때 ErrorCode 객체에 저장된 상세 메시지를 예외 객체의 메시지로 설정
 * 또한, 예외 처리시 ErrorCode 객체를 반환하기 위해
 * 해당 객체를 멤버 변수로 선언하고 getter 제공.
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
