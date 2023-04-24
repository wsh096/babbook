package com.zerobase.babbook.domain.dto;

import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.entity.Review;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 리뷰 정보를 담은 Review 엔티티로 데이터 조회를 위해 사용하는 DTO 클래스 String title: 리뷰 제목 String description: 리뷰 설명
 * double rate: 리뷰 평점
 * <p>
 * ReviewDto 클래스 내의 메소드 public static ReviewDto from(Review review) Review 엔티티를 통해 ReviewDto 객체를 생성
 * <p>
 * public static List<ReviewDto> fromList(List<Review> reviews) Review 엔티티 리스트를 이용하여 ReviewDto 객체
 * 리스트 생성 이때 최대 1000개까지만 가져오게 제한.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {


    private String title;
    private String description;
    private double rate;

    public static ReviewDto from(Review review) {

        return ReviewDto.builder()
            .title(review.getTitle())
            .description(review.getDescription())
            .rate(review.getRate())
            .build();
    }

    public static List<ReviewDto> fromList(List<Review> reviews) {
        List<ReviewDto> restaurantList = new ArrayList<>();

        int size = Math.min(1000, reviews.size());//임의제한
        for (int i = 0; i < size; i++) {
            Review review = reviews.get(i);
            ReviewDto eachRestaurantDto = ReviewDto.builder()
                .title(review.getTitle())
                .rate(review.getRate())
                .build();
            restaurantList.add(eachRestaurantDto);
        }
        return restaurantList;
    }

}
