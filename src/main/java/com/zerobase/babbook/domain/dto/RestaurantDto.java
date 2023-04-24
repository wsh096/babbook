package com.zerobase.babbook.domain.dto;

import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.entity.Review;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 *레스토랑 정보를 나타내는 DTO 클래스 데이터를 조회하는 용도로 사용
 *
 * 메서드
 *
 * public static RestaurantDto from(Restaurant restaurant)
 * Restaurant 엔티티 객체를 RestaurantDto 객체로 변환하는 역할
 * 구체적인 데이터에서 이름, 설명, 주소, 번호, `평점`만 보이면 되기 때문에 해당 부분만 구현
 *
 * public static List<RestaurantDto> fromList(List<Restaurant> restaurant)
 * Restaurant 엔티티 목록을 RestaurantDto 목록으로 변환해주는 역할
 *
 * public static List<String> nameList(List<Restaurant> restaurant)
 * Restaurant 엔티티 목록에서 레스토랑 이름만 추출하여 String 리스트로 반환해주는 역할
 * */
public class RestaurantDto {

    private String name;
    private String description;
    private String address;
    private String phone;
    private List<Review> reviews;
    private Double avgRate;

    public static RestaurantDto from(Restaurant restaurant) {

        return RestaurantDto.builder()
            .name(restaurant.getName())
            .description(restaurant.getDescription())
            .address(restaurant.getAddress())
            .phone(restaurant.getPhone())
            .reviews(restaurant.getReviews())
            .avgRate(restaurant.getAverageRate())
            .build();
    }


    public static List<RestaurantDto> fromList(List<Restaurant> restaurant) {
        List<RestaurantDto> restaurantList = new ArrayList<>();

        int size = Math.min(1000, restaurant.size());

        for (int i = 0; i < size; i++) {
            Restaurant eachRestaurant = restaurant.get(i);
            RestaurantDto eachRestaurantDto = RestaurantDto.builder()
                .name(eachRestaurant.getName())
                .avgRate(eachRestaurant.getAverageRate())
                .build();
            restaurantList.add(eachRestaurantDto);
        }
        return restaurantList;
    }

    public static List<String> nameList(List<Restaurant> restaurant) {
        List<String> restaurantNameList = new ArrayList<>();

        int size = Math.min(1000, restaurant.size());
        for (int i = 0; i < size; i++) {
            restaurantNameList.add(restaurant.get(i).getName());
        }
        return restaurantNameList;
    }
}
