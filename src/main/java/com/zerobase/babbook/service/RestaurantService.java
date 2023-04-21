package com.zerobase.babbook.service;

import static com.zerobase.babbook.exception.ErrorCode.ALREADY_REGISTER_RESTAURANT;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_CORRECT_ACCESS;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_OWNER;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_RESTAURANT;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.exception.CustomException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    //레스토랑 추가

    public String addRestaurant(RestaurantForm form, Long ownerId){
        ownerRepository.findById(ownerId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));

        if (isBusinessNumberExist(form.getBusinessNumber())) {
            throw new CustomException(ALREADY_REGISTER_RESTAURANT);
    }else {
        Restaurant restaurant = add(form, ownerId);
        String name = restaurant.getName();
        return name+ " 식당이 정상적으로 등록되었습니다.";
    }
}
    private Restaurant add(RestaurantForm form,Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        return restaurantRepository.save(Restaurant.of(form,owner));
    }
    private boolean isBusinessNumberExist(String businessNumber) {
        return restaurantRepository.findByBusinessNumber(businessNumber).isPresent();
    }

    //레스토랑 업데이트

    public String updateRestaurant(RestaurantForm form, Long ownerId) {
        Restaurant restaurant = restaurantRepository
            .findByBusinessNumber(form.getBusinessNumber())
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
        Restaurant check = restaurantRepository.findByOwnerId(
                ownerId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        if(!restaurant.equals(check)){//불일치
            throw new CustomException(DO_NOT_CORRECT_ACCESS);
        }else{
            restaurant = update(form, ownerId);
            return " 정상적으로 수정 되었습니다.";
        }
    }
    private Restaurant update(RestaurantForm form,Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        return restaurantRepository.save(Restaurant.of(form,owner));
    }
    //레스토랑 삭제

    public String delete(Long restaurantId, Long ownerId) {
        Restaurant restaurant = restaurantRepository
            .findById(restaurantId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
        Restaurant check = restaurantRepository.findByOwnerId(
                ownerId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        String name = restaurant.getName();
       if(!restaurant.equals(check)){//불일치
           throw new CustomException(DO_NOT_CORRECT_ACCESS);
       }else{
            restaurantRepository.deleteById(restaurantId);
           return name +" 식당이 정상적으로 삭제 되었습니다.";
       }
    }
    //간단하게 보이는 곳(전체 페이지 처리 필요.) 리스트//페이지 처리 필요. 별도로 만들기.
    public List<Restaurant> restaurantList() {//너무 많은 정보가 담기는 거 같긴 한데...
        return restaurantRepository.findAll();
    }
    //상세하게 보이게 하는 곳.(특정 데이터 전체 확인)

    public Restaurant restaurantDetail(Long restaurantId){
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
    }
}
