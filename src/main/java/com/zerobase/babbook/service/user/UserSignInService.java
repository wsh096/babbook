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

@Service
@RequiredArgsConstructor
public class UserSignInService {

    private final JwtAuthenticationProvider provider;
    private final UserRepository userRepository;
    private static final UserRole USER = UserRole.USER;

    @Transactional
    public String userLoginToken(SignInForm form) {
        User user = validateSignIn(form)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return provider.createToken(user.getEmail(), user.getId(), USER);
    }

    private Optional<User> validateSignIn(SignInForm form) {
        return userRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
    }
}
