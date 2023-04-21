package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.dto.RestaurantDto;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;//restaurant 패키지에 넣으면 에러가 나는 증상.
    @GetMapping
    public ResponseEntity<?> getRestaurant() {
        return ResponseEntity.ok().body(restaurantService.restaurantList());
    }
    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurantDetail(@PathVariable Long restaurantId) {
        return ResponseEntity.ok().body(restaurantService.restaurantDetail(restaurantId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantForm form, @RequestParam Owner owner) {
        return ResponseEntity.ok(restaurantService.addRestaurant(form, owner));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRestaurant(@RequestBody RestaurantForm form, @RequestParam Owner owner) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(form, owner));
    }

    @DeleteMapping("/delete/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId,@RequestParam Owner owner) {
        return ResponseEntity.ok(restaurantService.delete(restaurantId, owner));
    }

}
