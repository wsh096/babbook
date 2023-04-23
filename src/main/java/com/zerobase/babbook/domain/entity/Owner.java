package com.zerobase.babbook.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.babbook.domain.form.SignUpForm;
import com.zerobase.babbook.util.ValidationUtils;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "owner")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    @JsonIgnore
    private String password;
    @Column(unique = true)
    //010 011, 016, 017, 018, 019 가능
    @Pattern(regexp = "^(01[016-9])-(\\d{3,4})-(\\d{4})$", message = "휴대폰 번호 형식이 유효하지 않습니다.")
    private String phone;
    @Column(unique = true)
    private String businessNumber;
    private boolean partnership;
    @OneToMany(mappedBy = "owner")
    private List<Restaurant> restaurant;
    @OneToMany(mappedBy = "owner")
    private List<Book> book;

    public static Owner from(SignUpForm form) {
        return Owner.builder()
            .email(form.getEmail())
            .name(form.getName())
            .password(form.getPassword())
            .phone(form.getPhone())
            .partnership(false)//따로 승인 조건 없음. 해당 부분 가입과 동시에 바뀌게 만들기.
            .build();
    }
}
