package com.zerobase.babbook.service.restaurant;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.domain.reprository.ReviewRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//restaurant의 평균점수를 매일 한 번씩 조회해서 만들어줌.

@Service
@RequiredArgsConstructor
public class RestaurantSchedulerService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 0시마다 실행
    public void updateAverageRate() {
        List<Long> restaurantIds = reviewRepository.findRestaurantIdsWithReviews();
        for (Long restaurantId : restaurantIds) {
            Double averageRate = reviewRepository.findAverageRateByRestaurantId(restaurantId);
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT));
                restaurant.setAverageRate(averageRate);
                restaurantRepository.save(restaurant);
        }
    }

}