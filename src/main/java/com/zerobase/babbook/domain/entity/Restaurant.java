package com.zerobase.babbook.domain.entity;

import com.zerobase.babbook.domain.form.RestaurantForm;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 연관관계 설명.
 * Owner(ManyToOne), book(OneToMany), review(OneToMany)
 * 사업자 번호의 경우도 휴대폰 번호처럼 정규식으로 000-00-00000으로 둠.
 * 추후, 해당 값이 아니면 에러 발생하게 ErrorCode 작성.
 *
 * 메서드
 * public static Restaurant of(RestaurantForm form, Owner owner)
 * RestaurantForm 과 Owner 객체를 인자로 받아
 * 새로운 Restaurant 객체를 생성하고 반환
 * RestaurantForm 에서 받아온
 * name, businessNumber, description, address, phone 의 정보 가져와 반환
 */
@Getter
@Setter
@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Book> books;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews;
    private String name;
    @Column(length = 500)
    private String description;
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}", message = "사업자 등록 번호 형식이 유효하지 않습니다.")
    @Parameter(description = "사업자 등록 번호 (XXX-XX-XXXXX)", example = "123-45-67890", required = true)
    private String businessNumber;
    private String address;
    private String phone;
    private Double averageRate;

    public static Restaurant of(RestaurantForm form, Owner owner) {
        return Restaurant.builder()
            .name(form.getName())
            .businessNumber(form.getBusinessNumber())
            .description(form.getDescription())
            .address(form.getAddress())
            .phone(form.getPhone())
            .owner(owner)
            .averageRate(0.0)
            .build();
    }
}
