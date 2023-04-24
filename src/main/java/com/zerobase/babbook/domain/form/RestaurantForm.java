package com.zerobase.babbook.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * restaurant 엔티티 클래스의 restaurant 을 추가할 때 사용하는 form
 * 가게 이름, 설명, 사업자 번호(양식은 서비스에서 유효성 검증), 주소, 전화 번호를 입력.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantForm {
    private String name;
    private String description;
    private String businessNumber;
    private String address;
    private String phone;

}
