package com.zerobase.babbook.service.owner;

import static com.zerobase.babbook.exception.ErrorCode.ALREADY_REGISTER_USER;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerSignUpService {
    private final OwnerRepository ownerRepository;
    public String ownerSignUp(SignUpForm form) {
        if (isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Owner owner = signUp(form);
            owner.setPartnership(true);
            ownerRepository.save(owner); //따로 승인 조건이 없기 때문에 위와 같이 기본값을 변경하는 수준으로만 구현.
            return "회원 가입에 성공하였습니다.";
        }
    }
    public Owner signUp(SignUpForm form) {// 가입
        return ownerRepository.save(Owner.from(form));
    }
    public boolean isEmailExist(String email){
        return ownerRepository.findByEmail(email).isPresent();
    }
}
