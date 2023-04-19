package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.service.owner.OwnerSignUpService;
import com.zerobase.babbook.service.user.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final UserSignUpService userSignUpService;
    private final OwnerSignUpService ownerSignUpService;

    @PostMapping("/user")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(userSignUpService.userSignUp(form));
    }

    @PostMapping("/owner")
    public ResponseEntity<String> ownerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(ownerSignUpService.ownerSignUp(form));
    }


}
