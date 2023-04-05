package com.zerobase.babbook.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    EXCEPTION(HttpStatus.BAD_REQUEST, "예외"),
    EXCEPTION_EXAMPLE(HttpStatus.BAD_REQUEST, "예시");
    private final HttpStatus httpStatus;
    private final String detail;
}

