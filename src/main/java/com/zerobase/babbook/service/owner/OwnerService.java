package com.zerobase.babbook.service.owner;

import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.SignInForm;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private static final UserRole OWNER = UserRole.OWNER;
    private final JwtAuthenticationProvider provider;
    private final OwnerRepository ownerRepository;

    public Optional<Owner> findValidOwner(String email, String password) {
        return ownerRepository.findByEmail(email).stream()
            .filter(owner ->
                owner.getPassword().equals(password))
            .findFirst();
    }

    @Transactional
    public void update(Owner owner) {// 업데이트
        Owner o = ownerRepository.findById(owner.getId()).get();
    }

    @Transactional
    public void delete(Long id) {//회원 탈퇴
        ownerRepository.deleteById(id);
    }

}