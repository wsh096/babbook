package com.zerobase.babbook.domain.common;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("사용자"),
    Owner("점주");
    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
