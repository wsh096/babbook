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

/**
 * 사용자와 점주 회원가입 담당 API 컨트롤러
 * '/signup/user'와 '/signup/owner' 엔드포인트를 통해 사용자와 점주 회원가입에 필요한 정보를 전달
 *  각각의 서비스 클래스 호출해 회원가입 수행.
 *  회원가입이 성공시 '200 OK' 응답을 반환
 */
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
