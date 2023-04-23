package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Review;
import com.zerobase.babbook.domain.entity.User;
import feign.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰가 작성된 음식점의 ID 목록 조회
    @Query("SELECT DISTINCT r.id FROM Review r WHERE r.restaurant IS NOT NULL")
    List<Long> findRestaurantIdsWithReviews();

    // 음식점 ID에 해당하는 리뷰들의 평균 점수 조회
    @Query("SELECT AVG(r.rate) FROM Review r WHERE r.restaurant.id = :restaurantId")
    Double findAverageRateByRestaurantId(@Param("restaurant_id") Long restaurantId);
    List<Review> findByBook_User(User user);
}
