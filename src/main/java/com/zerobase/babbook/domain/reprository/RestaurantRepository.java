package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *Restaurant 엔티티를 처리하기 위한 Repository
 *
 * 순서대로 businessNumber를 찾아 오는 데 사용.
 *
 * 모든 식당의 이름을 조회하는 검색용으로 사용.(같은 식당 이름이 여러 개 일 수 있다는 생각에서 제작)
 *
 * 해당 식당의 점주를 찾아오는 기능
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByBusinessNumber(String businessNumber);
    Optional<List<Restaurant>> findAllByName(String name);
    Optional<Restaurant> findByOwner(Owner owner);
}
