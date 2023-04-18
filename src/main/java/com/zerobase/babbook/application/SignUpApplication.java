package com.zerobase.babbook.application;

import static com.zerobase.babbook.exception.ErrorCode.ALREADY_REGISTER_USER;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.service.OwnerService;
import com.zerobase.babbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final UserService userService;
    private final OwnerService ownerService;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    public String userSignUp(SignUpForm form) {
        if (userService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            User user = signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }
    public User signUp(SignUpForm form) {//회원 가입
        return userRepository.save(User.from(form));
    }
    public String ownerSignUp(SignUpForm form) {
        if (ownerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Owner owner = signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }
    public Owner signUp(SignUpForm form) {// 회원 가입
        return ownerRepository.save(Owner.from(form));
    }
}
