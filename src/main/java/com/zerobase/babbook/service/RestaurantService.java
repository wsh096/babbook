package com.zerobase.babbook.service;

import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_OWNER;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_RESTAURANT;

import com.zerobase.babbook.domain.dto.RestaurantDto;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    //레스토랑 추가
    @Transactional
    public Restaurant addRestaurant(RestaurantForm form,Owner owner ){
        Owner o = ownerRepository.findById(owner.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        //이미 있는 경우, 에러를 반환하고 싶음.
        return restaurantRepository.save(Restaurant.of(form,owner));
    }
    //레스토랑 업데이트
    @Transactional
    public Restaurant updateRestaurant(RestaurantForm form, Owner owner) {
        Owner o = ownerRepository.findById(owner.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        return restaurantRepository.save(Restaurant.of(form,owner));
    }
    //레스토랑 삭제
    @Transactional
    public void delete(Long restaurantId, Owner owner) {
        Owner o = ownerRepository.findById(owner.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        Restaurant restaurant = restaurantRepository
            .findById(restaurantId)
            .orElseThrow(()-> new CustomException(NOT_FOUND_RESTAURANT));
         restaurantRepository.deleteById(restaurantId);
    }
    //간단하게 보이는 곳(전체 페이지 처리 필요.) 리스트//페이지 처리 필요. 별도로 만들기.
    public List<Restaurant> restaurantList() {//너무 많은 정보가 담기는 거 같긴 한데...
        return restaurantRepository.findAll();
    }
    //상세하게 보이게 하는 곳.(특정 데이터 전체 확인)
    @Transactional
    public Restaurant restaurantDetail(Long restaurantId){
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
    }



}
