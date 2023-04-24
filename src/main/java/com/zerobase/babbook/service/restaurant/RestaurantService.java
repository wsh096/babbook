package com.zerobase.babbook.service.restaurant;

import com.zerobase.babbook.domain.dto.RestaurantDto;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JwtAuthenticationProvider provider;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    //레스토랑 추가
    public String addRestaurant(String token, RestaurantForm form) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto owner = provider.getUserDto(token);
        ownerRepository.findById(owner.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));

        if(!isValidBusinessNumber(form.getBusinessNumber())){
            throw new CustomException(ErrorCode.DO_NOT_VALID_BUSINESS_NUMBER);
        }

        if (isBusinessNumberExist(form.getBusinessNumber())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_BUSINESSNUMBER);
        } else {
            Restaurant restaurant = add(form, owner.getId());
            String name = restaurant.getName();
            return name + " 식당이 정상적으로 등록되었습니다.";
        }
    }

    private boolean isValidBusinessNumber(String businessNumber) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(businessNumber);
        return matcher.matches();
    }

    private Restaurant add(RestaurantForm form, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        return restaurantRepository.save(Restaurant.of(form, owner));
    }

    private boolean isBusinessNumberExist(String businessNumber) {
        return restaurantRepository.findByBusinessNumber(businessNumber).isPresent();
    }

    //레스토랑 업데이트
    @Transactional
    public String updateRestaurant(String token, RestaurantForm form) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto ownerCheck = provider.getUserDto(token);
        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));

        Restaurant restaurant = restaurantRepository
            .findByBusinessNumber(form.getBusinessNumber())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_CORRECT_BUSINESSNUMBER));

        Restaurant check = restaurantRepository.findByOwner(owner)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));

        if (!restaurant.equals(check)) {//불일치
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_ACCESS);
        } else {
            restaurant = update(form);
            return " 정상적으로 수정 되었습니다.";
        }
    }

    private Restaurant update(RestaurantForm form) {
        Restaurant restaurant =
            restaurantRepository.findByBusinessNumber(form.getBusinessNumber()).get();
        restaurant.setName(form.getName());
        restaurant.setDescription(form.getDescription());
        restaurant.setAddress(form.getAddress());
        return restaurantRepository.save(restaurant);
    }

    //레스토랑 삭제
    public String delete(String token, Long restaurantId) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto ownerCheck = provider.getUserDto(token);

        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));

        Restaurant restaurant = restaurantRepository
            .findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT));

        String name = restaurant.getName();

        if (!Objects.equals(restaurant.getOwner().getId(), owner.getId())) {//불일치
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_ACCESS);
        } else {
            restaurantRepository.deleteById(restaurantId);
            return name + " 식당이 정상적으로 삭제 되었습니다.";
        }
    }

    //간단하게 보이는 곳(전체 페이지 처리 필요.) 리스트
    // 페이지 처리 필요. 별도로 만들기
    // 추가 수정 이름과 평점으로 보이게
    // 평점의 경우 리뷰로 만들어지는 값.
    public List<RestaurantDto> restaurantList() {
        return RestaurantDto.fromList(restaurantRepository.findAll());
    }

    //상세하게 보이게 하는 곳.(특정 데이터 전체 확인)
    public RestaurantDto restaurantDetail(Long restaurantId) {
        return RestaurantDto.from(restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT)));
    }

    //    검색 like 와 같이 유사한 것까지 검색 되게 하고 싶으나 식당 이름을 상세하게 안다는 전제하에 진행.
    //    단, 같은 이름의 식당이 있을 수 있으므로 리스트로 반환.
    public List<String> restaurantNameSearch(String name) {
        return RestaurantDto.nameList(restaurantRepository
            .findAllByName(name)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT)));
    }
}
