package com.zerobase.babbook.service.user;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * 사용자 로그인에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class UserSignInService {

    private final JwtAuthenticationProvider provider;
    private final UserRepository userRepository;
    private static final UserRole USER = UserRole.USER;
    /**
     * userLoginToken 메서드로 로그인 폼(SignInForm) 전달 받아
     * 이메일과 비밀번호 일치 확인 후 token 을 만들고 반환.
     */
    @Transactional
    public String userLoginToken(SignInForm form) {
        User user = validateSignIn(form)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return provider.createToken(user.getEmail(), user.getId(), USER);
    }
    /**
     * 로그인의 유효성을 검사. email 과 비밀번호 일치 여부 userRepository 에서 수행.
     */
    private Optional<User> validateSignIn(SignInForm form) {
        return userRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
    }
}
