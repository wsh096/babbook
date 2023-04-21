package com.zerobase.babbook.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerobase.babbook.domain.form.SignUpForm;
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
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    @Column(unique = true)
    private String phone;
    @OneToMany(mappedBy = "user")
    private List<Book> book;

    public static User from(SignUpForm form) {
        return User.builder()
            .email(form.getEmail())
            .password(form.getPassword())
            .name(form.getName())
            .phone(form.getPhone())
            .build();
    }

}
