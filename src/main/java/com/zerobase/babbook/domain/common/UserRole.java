package com.zerobase.babbook.domain.common;

import lombok.Getter;

/**
 * 단순하게 로그인에서 역할을 나누기 위한 기능을 수행.
 */
@Getter
public enum UserRole {
    USER("사용자"),
    OWNER("점주");
    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
