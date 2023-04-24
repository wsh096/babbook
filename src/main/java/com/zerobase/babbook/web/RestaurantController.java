package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.service.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * restaurant 관련 담당 API 컨트롤러
 */
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final String TOKEN = "X-AUTH-TOKEN";
    private final RestaurantService restaurantService;
    /**
     * restaurant 목록이 나오는 API 기본 restaurant 에 바로 나오게 만들어 둠.
     */
    @GetMapping
    public ResponseEntity<?> getRestaurant() {
        return ResponseEntity.ok().body(restaurantService.restaurantList());
    }
    /**
     * restaurant 이름 검색 API
     */
    @GetMapping("search/{restaurantName}")
    public ResponseEntity<?> getRestaurantDetail(@PathVariable String restaurantName) {
        return ResponseEntity.ok()
            .body(restaurantService.restaurantNameSearch(restaurantName));
    }
    /**
     * restaurant 상세 조회 API
     */
    @GetMapping("detail/{restaurant_id}")
    public ResponseEntity<?> getRestaurantDetail(
        @PathVariable(name = "restaurant_id") Long restaurantId) {
        return ResponseEntity.ok()
            .body(restaurantService.restaurantDetail(restaurantId));
    }
    /**
     * restaurant 추가 API
     */
    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@RequestHeader(name = TOKEN) String token,
        @RequestBody RestaurantForm form) {
        return ResponseEntity.ok(restaurantService.addRestaurant(token, form));
    }
    /**
     * restaurant 수정 API
     */
    @PutMapping("/modify/{restaurant_id}")
    public ResponseEntity<?> updateRestaurant(@RequestHeader(name = TOKEN) String token,
        @RequestBody RestaurantForm form,
        @PathVariable(name = "restaurant_id") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(token, form, restaurantId));
    }
    /**
     * restaurant 삭제 API
     */
    @DeleteMapping("/delete/{restaurant_id}")
    public ResponseEntity<?> deleteRestaurant(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.delete(token, restaurantId));
    }

}
