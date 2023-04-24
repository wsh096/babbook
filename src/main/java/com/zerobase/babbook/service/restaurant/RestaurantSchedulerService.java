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

/**
 * restaurant 평점을 업데이트 해주는 스케쥴러 서비스로
 * review 작성이 완료된 restaurant 의 평균을 repository 에서 db에서 만들어
 * 해당 값을 저장해주는 역할 매일 0시 마다 실행 되며
 * ?는 cron 표현식에서 일(day of the month) 필드를 의미하며, 이 경우 매일을 의미
 */
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