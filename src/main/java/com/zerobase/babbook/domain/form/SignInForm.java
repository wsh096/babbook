package com.zerobase.babbook.domain.form;

import lombok.Getter;
/**
 * user, owner 가 로그인 할 때 사용되는 값을 이용하는 form
 */
@Getter
public class SignInForm {
    private String email;
    private String password;
}

