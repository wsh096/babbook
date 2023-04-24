package com.zerobase.babbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Long id : 사용자 식별자 id
 * String email : 사용자 이메일 주소
 */
@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String email;
}
