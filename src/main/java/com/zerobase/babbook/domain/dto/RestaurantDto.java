package com.zerobase.babbook.domain.dto;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
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
/*
 * 해당 부분은 데이터를 가지고 조회하는 용도로 사용.
 * 구체적인 데이터에서 이름, 설명, 주소, 번호만 보이면 되기 때문에, 해당 부분만 구현
 *
 * 리스트 페이지는 적은 데이터만 가져오면 되기 때문에 null 값이 들어가는 형태가 되긴 하지만 List 로 만들어 구현
 * */
public class RestaurantDto {


    private String name;
    private String description;
    private String address;
    private String phone;

    //해당 부분은 데이터를 가지고 조회하는 용도로 사용.
//구체적인 데이터에서 이름, 설명, 주소, 번호만 보이면 되기 때문에, 해당 부분만 구현
    public static RestaurantDto from(Restaurant restaurant) {

        return RestaurantDto.builder()
            .name(restaurant.getName())
            .description(restaurant.getDescription())
            .address(restaurant.getAddress())
            .phone(restaurant.getPhone())
            .build();
    }

    //리스트 페이지는 적은 데이터만 가져오면 되기 때문에 null 값이 들어가는 형태가 되긴 하지만 List 로 만들어 구현
    public static List<RestaurantDto> fromList(List<Restaurant> restaurant) {
        List<RestaurantDto> restaurantList = new ArrayList<>();
        int size = Math.min(1000,
            restaurant.size());//우선 최대 1000개까지만 너무 많은 데이터가 들어가지 않게//이후 페이지 처리 부분 만들어 해결하기.
        for (int i = 0; i < size; i++) {
            Restaurant eachRestaurant = restaurant.get(i);
            RestaurantDto eachRestaurantDto = RestaurantDto.builder()
                .name(eachRestaurant.getName())
                .phone(eachRestaurant.getPhone())
                .address(eachRestaurant.getAddress())
                .build();
            restaurantList.add(eachRestaurantDto);
        }
        return restaurantList;
    }

    public static List<String> nameList(List<Restaurant> restaurant) {
        List<String> restaurantNameList = new ArrayList<>();
        int size = Math.min(1000,
            restaurant.size());//우선 최대 1000개까지만 너무 많은 데이터가 들어가지 않게//이후 페이지 처리 부분 만들어 해결하기.
        for (int i = 0; i < size; i++) {
            restaurantNameList.add(restaurant.get(i).getName());
        }

        return restaurantNameList;
    }
}
