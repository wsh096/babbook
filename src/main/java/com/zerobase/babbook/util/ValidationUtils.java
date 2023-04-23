package com.zerobase.babbook.util;

public class ValidationUtils {
    // 사업자 등록 번호 유효성 검사
    public static boolean isValidBusinessNumber(String businessNumber) {
        // XXX-XX-XXXXX 형식의 유효성 검사
        return businessNumber != null && businessNumber.matches("\\d{3}-\\d{2}-\\d{5}");
    }

    // 휴대폰 번호 유효성 검사
    public static boolean isValidPhoneNumber(String phone) {
        // 010, 011, 019로 시작하는 11자리 숫자인 경우 유효
        return phone != null && phone.matches("(010|011|019)-\\d{4}-\\d{4}");
    }
}
