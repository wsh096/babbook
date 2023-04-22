package com.zerobase.babbook.domain.entity;

import com.zerobase.babbook.domain.form.RestaurantForm;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Restaurant extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "restaurant")
    private List<Book> books;
    private String name;
    @Column(length = 500)
    private String description;
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}", message = "사업자 등록 번호 형식이 유효하지 않습니다.")
    @ApiModelProperty(value = "사업자 등록 번호 (XXX-XX-XXXXX)", example = "123-45-67890", required = true)
    private String businessNumber;
    private String address;
    private String phone;

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

    public boolean isValidBusinessNumber() {
        // XXX-XX-XXXXX 형식의 유효성 검사
        return businessNumber != null && businessNumber.matches("\\d{3}-\\d{2}-\\d{5}");
    }
}
