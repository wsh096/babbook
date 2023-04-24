package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Owner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *Owner 엔티티를 처리하기 위한 Repository
 *
 * owner SignIn 과,
 * owner signUp 에서 사용
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmailAndPassword(String email, String password);
    Optional<Owner> findByEmail(String email);
}
