package com.zerobase.babbook.service.owner;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * 점주 회원 가입에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class OwnerSignUpService {

    private final OwnerRepository ownerRepository;
    /**
     * ownerSignUp 메서드로 회원가입 폼(SignUpForm) 전달 받아
     * 이메일 중복 여부, 전화번호 유효성을 체크하고 회원 정보를 저장.
     */
    @Transactional
    public String ownerSignUp(SignUpForm form) {
        if (isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        }
        if (!isValidPhoneNumber(form.getPhone())) {
            throw new CustomException(ErrorCode.DO_NOT_VALID_PHONE_NUMBER);
        } else {
            Owner owner = signUp(form);
            owner.setPartnership(true);
            ownerRepository.save(owner); //따로 승인 조건이 없기 때문에 위와 같이 기본값을 변경하는 수준으로만 구현.
            return "회원 가입에 성공하였습니다.";
        }
    }


    /**
     * 회원 가입 저장
     */
    public Owner signUp(SignUpForm form) {// 가입
        return ownerRepository.save(Owner.from(form));
    }
    /**
     * 이메일 유효성 검사
     */
    public boolean isEmailExist(String email) {
        return ownerRepository.findByEmail(email).isPresent();
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
