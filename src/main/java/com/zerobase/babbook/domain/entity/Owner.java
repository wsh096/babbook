package com.zerobase.babbook.domain.entity;

import com.zerobase.babbook.domain.form.SignUpForm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "owner")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    @Column(unique = true)
    private String phone;
    private String password;
    @Column(unique = true)
    private String businessNumber;
    private boolean partnershipStatus;
    public static Owner from(SignUpForm form) {
        return Owner.builder()
            .email(form.getEmail())
            .password(form.getPassword())
            .name(form.getName())
            .phone(form.getPhone())
            .partnershipStatus(false)
            .build();
    }

    @OneToMany(mappedBy = "owner")
    private Collection<Restaurant> restaurant = new ArrayList<>();

    public Collection<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Collection<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }
}
