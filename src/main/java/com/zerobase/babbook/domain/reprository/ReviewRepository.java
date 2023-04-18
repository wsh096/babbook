package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //내 리뷰 보기
    @Query(value = "select sc from Review sc where user_id=?1", nativeQuery = false)
    public List<Review> findByUserReview(Long user_id);

    public Page<Review> findByUser_id(Long user_id, Pageable pageable);

}
