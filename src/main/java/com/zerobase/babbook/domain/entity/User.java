package com.zerobase.babbook.domain.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userMail;
    private String password;
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)//사용 후 삭제
    private List<Book> book;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Restaurant> restaurants;
}
