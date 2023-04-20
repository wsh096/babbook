package com.zerobase.babbook.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    //login_logout
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 등록된 메일입니다."),
    EXCEPTION(HttpStatus.BAD_REQUEST, "예외"),

    //Restaurant 관련
    NOT_FOUND_RESTAURANT(HttpStatus.BAD_REQUEST, "존재하지 않는 식당입니다."),
    EXCEPTION_EXAMPLE(HttpStatus.BAD_REQUEST, "예시");
    private final HttpStatus httpStatus;
    private final String detail;
}

