package com.zerobase.babbook.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    //login_logout(User(사용자), Owner(점주))
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    NOT_FOUND_OWNER(HttpStatus.BAD_REQUEST, "존재하지 않는 사업자입니다."),
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 등록된 메일입니다. 다른 메일로 시도해 주세요."),


    //Restaurant 관련(식당)
    NOT_FOUND_RESTAURANT(HttpStatus.BAD_REQUEST, "존재하지 않는 식당입니다."),
    NOT_CORRECT_BUSINESSNUMBER(HttpStatus.BAD_REQUEST, "사업자 번호가 일치하지 않습니다. 사업자 번호를 확인해 주십시오."),
    ALREADY_REGISTER_BUSINESSNUMBER(HttpStatus.BAD_REQUEST,"이미 등록된 사업자 번호입니다. 사업자 번호를 다시 한 번 확인해 주십시오."),
    DO_NOT_CORRECT_ACCESS(HttpStatus.BAD_REQUEST, "등록하신 분이 아닙니다. 비정상적인 접근입니다."),



    //Book 관련(예약)
    BAD_ACCESS_TIME(HttpStatus.BAD_REQUEST, "현재 시간보다 이전은 예약할 수 없습니다. 현재 시간 보다 30분 이후의 시간부터 예약이 가능합니다."),
    DO_NOT_ACCESS_BOOK_TIME(HttpStatus.BAD_REQUEST, "현재 시간 보다 30분 이전은 예약할 수 없습니다. 현재 시간 보다 30분 이후의 시간부터 예약이 가능합니다."),
    DO_NOT_ACCESS_BOOK_TIME(HttpStatus.BAD_REQUEST, " 비정상적인 접근입니다."),
    DO_NOT_ACCESS_BOOK_TIME(HttpStatus.BAD_REQUEST, " 비정상적인 접근입니다."),
    EXCEPTION_EXAMPLE(HttpStatus.BAD_REQUEST, "끝맺는 예시");
    private final HttpStatus httpStatus;
    private final String detail;
}

