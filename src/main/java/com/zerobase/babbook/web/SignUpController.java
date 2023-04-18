package com.zerobase.babbook.web;

import com.zerobase.babbook.application.SignUpApplication;
import com.zerobase.babbook.domain.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpApplication signUpApplication;

    @PostMapping("/user")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.userSignUp(form));
    }

    @PostMapping("/owner")
    public ResponseEntity<String> ownerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.ownerSignUp(form));
    }


}
