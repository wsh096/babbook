package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.service.owner.OwnerSignInService;
import com.zerobase.babbook.service.user.UserSignInService;
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

    private final UserSignInService userSignInService;
    private final OwnerSignInService ownerSignInService;

    @PostMapping("/user")
    public ResponseEntity<String> signUser(@RequestBody SignInForm form) {
        return ResponseEntity.ok(userSignInService.userLoginToken(form));
    }

    @PostMapping("/owner")
    public ResponseEntity<String> signOwner(@RequestBody SignInForm form) {
        return ResponseEntity.ok(ownerSignInService.ownerLoginToken(form));
    }

}