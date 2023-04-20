package com.zerobase.babbook.service.restaurant;

import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_RESTAURANT;

import com.zerobase.babbook.domain.dto.RestaurantDto;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.exception.CustomException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    @Transactional
    public RestaurantDto add( RestaurantForm form){
        return restaurantRepository.save(RestaurantDto.from(form));
    }
    @Transactional
    public Restaurant getByRestaurantId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
    }
}
