package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.dto.RestaurantDto;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.form.RestaurantForm;
import com.zerobase.babbook.service.restaurant.RestaurantService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final RestaurantService restaurantService;


    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@RequestBody RestaurantForm form, @RequestParam Owner owner) {
        return ResponseEntity.ok(200);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateRestaurant(@RequestBody RestaurantForm form, @RequestParam Owner owner) {
        return ResponseEntity.ok(200);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRestaurant(@RequestBody RestaurantForm form, @RequestParam Owner owner) {
        return ResponseEntity.ok(200);
    }
}
