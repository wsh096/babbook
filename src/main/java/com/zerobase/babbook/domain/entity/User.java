package com.zerobase.babbook.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.babbook.domain.form.SignUpForm;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 * User 테이블에 관한 엔티티 클래스
 * emaild을 유니크하게 설정
 * phone의 경우 유효성을 주입하고 해당, 값의 정규식에서 010,011,016,017,018,019-000(0)-0000 형태로 제작
 * 지연 로딩 및 고아객체 삭제 역시 연관관계 매핑된 값들은 설정.
 *
 * 메서드
 * public static User from(SignUpForm form)
 *  SignUpForm에서 받아온 정보를 사용해
 *  User 객체를 생성 하고 반환
 *  이 때, 사용된 정보는 이메일, 이름, 비밀번호, 전화번호의 기본적인 값만 있음
 */
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Book> book;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    @Column(unique = true)
    @Pattern(regexp = "^(01[016-9])-(\\d{3,4})-(\\d{4})$", message = "휴대폰 번호 형식이 유효하지 않습니다.")
    private String phone;

    public static User from(SignUpForm form) {
        return User.builder()
            .email(form.getEmail())
            .password(form.getPassword())
            .name(form.getName())
            .phone(form.getPhone())
            .build();
    }

}
