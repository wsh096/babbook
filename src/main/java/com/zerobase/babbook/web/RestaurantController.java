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

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final String TOKEN = "X-AUTH-TOKEN";
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<?> getRestaurant() {
        return ResponseEntity.ok().body(restaurantService.restaurantList());
    }

    @GetMapping("search/{restaurantName}")
    public ResponseEntity<?> getRestaurantDetail(@PathVariable String restaurantName) {
        return ResponseEntity.ok()
            .body(restaurantService.restaurantNameSearch(restaurantName));
    }

    @GetMapping("detail/{restaurant_id}")
    public ResponseEntity<?> getRestaurantDetail(
        @PathVariable(name = "restaurant_id") Long restaurantId) {
        return ResponseEntity.ok()
            .body(restaurantService.restaurantDetail(restaurantId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@RequestHeader(name = TOKEN) String token,
        @RequestBody RestaurantForm form) {
        return ResponseEntity.ok(restaurantService.addRestaurant(token, form));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRestaurant(@RequestHeader(name = TOKEN) String token,
        @RequestBody RestaurantForm form) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(token, form));
    }

    //제대로 삭제 안 되고 있음 TODO
    @DeleteMapping("/delete/{restaurant_id}")
    public ResponseEntity<?> deleteRestaurant(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.delete(token, restaurantId));
    }

}
