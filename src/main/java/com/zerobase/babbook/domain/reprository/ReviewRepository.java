package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
