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
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Review> reviews;
    private String name;
    @Column(length = 500)
    private String description;
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}", message = "사업자 등록 번호 형식이 유효하지 않습니다.")
    @Parameter(description = "사업자 등록 번호 (XXX-XX-XXXXX)", example = "123-45-67890", required = true)
    private String businessNumber;
    private String address;
    private String phone;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double averageRate;//리뷰에 의해서 (하루 한 번) 값 자동 변경
    //변경 사항이 없으면 넘어가는 방식으로 redis 활용 가능해 보임.

    public static Restaurant of(RestaurantForm form, Owner owner) {
        return Restaurant.builder()
            .name(form.getName())
            .businessNumber(form.getBusinessNumber())
            .description(form.getDescription())
            .address(form.getAddress())
            .phone(form.getPhone())
            .owner(owner)
            .build();
    }
}
