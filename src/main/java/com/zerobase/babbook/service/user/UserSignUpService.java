package com.zerobase.babbook.service.user;

import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사용자 회원 가입에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserRepository userRepository;

    /**
     * userSignUp 메서드로 회원가입 폼(SignUpForm) 전달 받아
     * 이메일 중복 여부, 전화번호 유효성을 체크하고 회원 정보를 저장.
     */
    @Transactional
    public String userSignUp(SignUpForm form) {
        if (isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }
        if (!isValidPhoneNumber(form.getPhone())) {
            throw new CustomException(ErrorCode.DO_NOT_VALID_PHONE_NUMBER);
        } else {
            signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }

    /**
     * 회원 가입 저장
     */
    private User signUp(SignUpForm form) {
        return userRepository.save(User.from(form));
    }

    /**
     * 이메일 유효성 검사
     */
    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 전화번호 유효성 검사
     */
    private boolean isValidPhoneNumber(String phone) {
        String regex = "^(01[016-9])-(\\d{3,4})-(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}
