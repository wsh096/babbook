package com.zerobase.babbook.domain.form;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * user, owner 가 가입 할 때 공통으로 사용하는 값이 있는 form
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    private String email;
    private String name;
    private String password;
    private String phone;
}

