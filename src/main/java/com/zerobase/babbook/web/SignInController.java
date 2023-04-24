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
/**
 * 사용자와 점주 로그인 담당 API 컨트롤러
 * '/signin/user'와 '/signin/owner' 엔드포인트를 통해 사용자와 점주 로그인에 필요한 정보를 전달
 *  각각의 서비스 클래스 호출해 로그인 수행.
 *  로그인 성공시 '200 OK' 응답을 반환
 */

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