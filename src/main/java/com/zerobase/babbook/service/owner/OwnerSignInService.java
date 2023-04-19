package com.zerobase.babbook.service.owner;

import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerSignInService {

    private final JwtAuthenticationProvider provider;
    private final OwnerRepository ownerRepository;
    private static final UserRole OWNER = UserRole.OWNER;

    public String ownerLoginToken(SignInForm form) {
        Owner owner = validateSignIn(form).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return provider.createToken(owner.getEmail(), owner.getId(), OWNER);
    }

    private Optional<Owner> validateSignIn(SignInForm form) {
        return ownerRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
    }
}
