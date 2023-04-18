package com.zerobase.babbook.application;

import static com.zerobase.babbook.domain.common.UserRole.OWNER;
import static com.zerobase.babbook.domain.common.UserRole.USER;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.service.OwnerService;
import com.zerobase.babbook.service.UserService;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final UserService userService;
    private final JwtAuthenticationProvider provider;
    private final OwnerService ownerService;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    public String signIn(SignInForm form) {//로그인

        User user = validateSignIn(form).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return provider.createToken(user.getEmail(), user.getId(), USER);
    }

    private Optional<User> validateSignIn(SignInForm form) {
        return userRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
    }

    public Optional<User> findValidUser(String email, String password) {
        return userRepository.findByEmail(email).stream()
            .filter(user ->
                user.getPassword().equals(password))
            .findFirst();
    }

    public String userLoginToken(SignInForm form) {
        //1. 로그인 가능 여부

        public String signIn (SignInForm form){// 로그인

            Owner owner = validateSignIn(form).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER));
            return provider.createToken(owner.getEmail(), owner.getId(), OWNER);
        }

        private Optional<Owner> validateSignIn (SignInForm form){
            return ownerRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
        }
    }