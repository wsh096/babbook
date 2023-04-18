package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Owner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmailAndPassword(String email, String password);
    Optional<Owner> findByEmail(String email);
}
