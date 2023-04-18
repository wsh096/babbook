package com.zerobase.babbook.web;

import com.zerobase.babbook.application.SignInApplication;
import com.zerobase.babbook.domain.form.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {
    private final SignInApplication signInApplication;
    @PostMapping("/user")
    public ResponseEntity<String> signUser(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.userLoginToken(form));
    }
    @PostMapping("/owner")
    public ResponseEntity<String> signOwner(@RequestBody SignInForm form){
        return ResponseEntity.ok(signInApplication.ownerLoginToken(form));
    }

}