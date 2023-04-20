package com.zerobase.babbook.domain.dto;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;

    public static RestaurantDto from(Restaurant restaurant) {

        return RestaurantDto.builder()
            .id(restaurant.getId())
            .name(restaurant.getName())
            .address(restaurant.getAddress())
            .description(restaurant.getDescription())
            .build();
    }
}
