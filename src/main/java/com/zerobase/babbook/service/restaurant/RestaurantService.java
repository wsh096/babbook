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

/**
 * 식당에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JwtAuthenticationProvider provider;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    /**
     * 식당 전체 목록 확인
     */
    public List<RestaurantDto> restaurantList() {
        return RestaurantDto.fromList(restaurantRepository.findAll());
    }
    /**
     * 식당 상세 확인
     * 찾는 식당이 없으면 에러
     */
    public RestaurantDto restaurantDetail(Long restaurantId) {
        return RestaurantDto.from(restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT)));
    }

    /**
     * 식당 이름 검색
     * 단, 같은 이름의 식당이 있을 수 있으므로, List로 만들며,
     * 해당 값은 null을 반환할 수 있음.
     */
    public List<String> restaurantNameSearch(String name) {
        return RestaurantDto.nameList(restaurantRepository
            .findAllByName(name)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT)));
    }
    /**
     * 레스토랑 추가
     * 토큰 유효성 검사, 점주 확인,
     * 비즈니스 넘버 유효성 확인,
     * 이미 등록된 사업자 번호인지 확인.
     */
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

    /**
     * 비즈니스 넘버 유효성 확인
     */
    private boolean isValidBusinessNumber(String businessNumber) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{5}");
        Matcher matcher = pattern.matcher(businessNumber);
        return matcher.matches();
    }

    /**
     * 레스토랑 저장 메서드
     */
    private Restaurant add(RestaurantForm form, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        return restaurantRepository.save(Restaurant.of(form, owner));
    }

    /**
     * 이미 등록된 사업자 번호인지 확인.
     */
    private boolean isBusinessNumberExist(String businessNumber) {
        return restaurantRepository.findByBusinessNumber(businessNumber).isPresent();
    }

    /**
     * 레스토랑 업데이트
     * 업데이트이기 때문에 추가와 다른 점은 businessnumber가 기존의 id의 값과
     * 다르다는 것으로 표시가 가능.
     */
    @Transactional
    public String updateRestaurant(String token, RestaurantForm form,Long restaurantId) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto ownerCheck = provider.getUserDto(token);
        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));

        if(!isValidBusinessNumber(form.getBusinessNumber())){
            throw new CustomException(ErrorCode.DO_NOT_VALID_BUSINESS_NUMBER);
        }
        Restaurant restaurant = restaurantRepository
            .findByBusinessNumber(form.getBusinessNumber())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_CORRECT_BUSINESSNUMBER));
        /**
         * 수정하고자 하는 식당의 넘버도 유효한지 확인합니다.
         */
        Restaurant wantToModify = restaurantRepository.findById(restaurantId)
            .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT));
        /**
         * 여러 식당을 가지고 있기 때문에 자신이 수정하고자하는 식당과 일치해야 합니다.
         */
        if(!Objects.equals(form.getBusinessNumber(), wantToModify.getBusinessNumber())){
            throw new CustomException(ErrorCode.DO_NOT_MATCH_RESTAURANT);
        }
        /**
         * 수정하는 사업자 역시 일치해야 합니다.
         */
        Restaurant checkOwner = restaurantRepository.findByOwner(owner)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));
        /**
         * 2개의 restaurant이 다른 상태라면 수정을 하면 안 됨.
         */
        if (restaurant != checkOwner) {//불일치
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_ACCESS);
        } else {
            update(form);
            return " 정상적으로 수정 되었습니다.";
        }
    }

    /**
     * 레스토랑 업데이트
     */
    private void update(RestaurantForm form) {
        Restaurant restaurant =
            restaurantRepository.findByBusinessNumber(form.getBusinessNumber()).get();
        restaurant.setName(form.getName());
        restaurant.setDescription(form.getDescription());
        restaurant.setAddress(form.getAddress());
        restaurantRepository.save(restaurant);
    }

    /**
     * 레스토랑 삭제
     */
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

        if(!isValidBusinessNumber(restaurant.getBusinessNumber())){
            throw new CustomException(ErrorCode.DO_NOT_VALID_BUSINESS_NUMBER);
        }

        String name = restaurant.getName();
        /**
         * 삭제하는 사람과 작성자의 일치 여부 확인
         * 불일치할 경우 삭제 못하게 막기.
         */
        if (!Objects.equals(restaurant.getOwner().getId(), owner.getId())) {
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_ACCESS);
        } else {
            restaurantRepository.deleteById(restaurantId);
            return name + " 식당이 정상적으로 삭제 되었습니다.";
        }
    }

}
