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

@Service
@RequiredArgsConstructor
public class OwnerSignUpService {

    private final OwnerRepository ownerRepository;

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

    private boolean isValidPhoneNumber(String phone) {
        String regex = "^(01[016-9])-(\\d{3,4})-(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public Owner signUp(SignUpForm form) {// 가입
        return ownerRepository.save(Owner.from(form));
    }

    public boolean isEmailExist(String email) {
        return ownerRepository.findByEmail(email).isPresent();
    }
}
