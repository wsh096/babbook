package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *User 엔티티를 처리하기 위한 Repository
 *
 * user SignIn 과,
 * user signUp 에서 사용
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
