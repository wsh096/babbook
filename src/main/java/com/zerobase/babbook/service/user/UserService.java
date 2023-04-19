package com.zerobase.babbook.service.user;

import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final UserRole USER = UserRole.USER;
    private final JwtAuthenticationProvider provider;
    private final UserRepository userRepository;


    @Transactional
    public void update(User user) {// 업데이트
        User u = userRepository.findById(user.getId()).get();
    }

    @Transactional
    public void delete(Long id) {//회원 탈퇴
        userRepository.deleteById(id);
    }

}
