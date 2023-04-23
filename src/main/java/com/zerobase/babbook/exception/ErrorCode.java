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
    NOT_FOUND_BOOK(HttpStatus.BAD_REQUEST, "존재하지 않는 예약입니다."),
    BAD_ACCESS_TIME(HttpStatus.BAD_REQUEST, "현재 시간보다 이전은 예약할 수 없습니다. 현재 시간 보다 30분 이후의 시간부터 예약이 가능합니다."),
    DO_NOT_ACCESS_BOOK_TIME(HttpStatus.BAD_REQUEST, "현재 시간 보다 30분 이전은 예약할 수 없습니다. 현재 시간 보다 30분 이후의 시간부터 예약이 가능합니다."),
    DO_NOT_CORRECT_BOOK_RESPONSE(HttpStatus.BAD_REQUEST, "예약에 관한 적절한 응답이 아닙니다. \"승인\" 또는 \"거절\"을 선택해 주십시오."),
    DO_NOT_WAIT_REQUEST(HttpStatus.BAD_REQUEST, "예약 대기 상태가 아닙니다. 잘못된 요청입니다."),
    EXCEPTION_EXAMPLE(HttpStatus.BAD_REQUEST, "예약에 관한 적절한 응답이 아닙니다. 승인 또는 거절을 선택해 주십시오."),
    DO_NOT_CORRECT_BOOK_CANCEL(HttpStatus.BAD_REQUEST, "예약자가 아니기 때문에 취소할 수 없습니다."),
    ALREADY_CANCEL_BOOK(HttpStatus.BAD_REQUEST, "해당 예약은 이미 취소된 예약입니다."),
    NOT_BOOK_IS_DO_NOT_ALLOW_CANCEL(HttpStatus.BAD_REQUEST, "해당 예약은 "),
    EXAMPLE(HttpStatus.BAD_REQUEST, " 비정상적인 접근입니다."),


    //Kiosk
    //조회에서 필요하지 않은 이유는 따로, 해당 내역을 조회한다고 해서 의미가 있는 게 아니기 때문.
    NO_MATCH_BOOK_AND_USER(HttpStatus.BAD_REQUEST, "예약자와 예약내역이 일치하지 않습니다."),
    NO_ACCEPT_BOOK(HttpStatus.BAD_REQUEST, "아직 승인되지 않은 예약은 사용할 수 없습니다."),
    NO_USE_BOOK(HttpStatus.BAD_REQUEST, "해당 예약은 사용되었거나 취소되었기에 사용할 수 없습니다."),

    //Review
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST, "해당 리뷰가 존재하지 않습니다."),
    ALREADY_WRITE_REVIEW(HttpStatus.BAD_REQUEST, "해당 리뷰는 이미 작성 완료 되었습니다."),
    TIME_OVER_WRITE_REVIEW(HttpStatus.BAD_REQUEST, "해당 리뷰는 작성 가능 기한을 넘었습니다."),
    NO_WRITE_REVIEW(HttpStatus.BAD_REQUEST, "해당 예약 건은 리뷰를 작성할 수 없습니다."),
    NOT_ALLOW_POINT(HttpStatus.BAD_REQUEST, "리뷰 평점은 0~10점까지 입력 가능합니다."),
    EXCEPTION_SAMMPLE(HttpStatus.BAD_REQUEST, "끝맺는 예시");
    private final HttpStatus httpStatus;
    private final String detail;
}

