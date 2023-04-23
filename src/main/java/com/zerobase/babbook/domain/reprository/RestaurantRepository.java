package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByBusinessNumber(String businessNumber);
    Optional<List<Restaurant>> findAllByName(String name);
    Optional<Restaurant> findByOwner(Owner owner);
}
