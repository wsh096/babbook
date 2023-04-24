package com.zerobase.babbook.service.owner;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * 점주 로그인에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class OwnerSignInService {

    private final JwtAuthenticationProvider provider;
    private final OwnerRepository ownerRepository;
    private static final UserRole OWNER = UserRole.OWNER;
    /**
     * ownerLoginToken 메서드로 로그인 폼(SignInForm) 전달 받아
     * 이메일과 비밀번호 일치 확인 후 token 을 만들고 반환.
     */
    @Transactional
    public String ownerLoginToken(SignInForm form) {
        Owner owner = validateSignIn(form)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));
        return provider.createToken(owner.getEmail(), owner.getId(), OWNER);
    }

    /**
     * 로그인의 유효성을 검사. email 과 비밀번호 일치 여부 ownerRepository 에서 수행.
     */
    private Optional<Owner> validateSignIn(SignInForm form) {
        return ownerRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
    }
}
