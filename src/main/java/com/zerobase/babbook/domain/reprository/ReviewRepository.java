package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Review;
import com.zerobase.babbook.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findByBook_User(User user);
}
