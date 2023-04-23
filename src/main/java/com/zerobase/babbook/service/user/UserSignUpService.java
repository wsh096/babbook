package com.zerobase.babbook.service.user;

import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserRepository userRepository;

    @Transactional
    public String userSignUp(SignUpForm form) {
        if (isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            User user = signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }

    private User signUp(SignUpForm form) {//회원 가입 저장

        return userRepository.save(User.from(form));
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


}
