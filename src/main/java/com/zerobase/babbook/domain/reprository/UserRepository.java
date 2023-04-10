package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUserMail(String userMail);
}
