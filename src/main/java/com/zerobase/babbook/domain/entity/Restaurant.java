package com.zerobase.babbook.domain.entity;

import com.zerobase.babbook.domain.form.RestaurantForm;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    private String name;
    @Column(length = 500)
    private String description;
    private Long oId;
    private String businessNumber;
    private String address;
    private String phone;
    @ManyToOne
    private Owner owner;
    public static Restaurant of(RestaurantForm form,Long ownerId) {
        return Restaurant.builder()
            .name(form.getName())
            .businessNumber(form.getBusinessNumber())
            .description(form.getDescription())
            .address(form.getAddress())
            .phone(form.getPhone())
            .oId(ownerId)
            .build();
    }
}
