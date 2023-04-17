package com.zerobase.babbook.domain.entity;

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
    @Column(unique = true)
    private String userMail;
    private String password;
    @Column(unique = true)
    private String phone;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.REMOVE)//사용 후 삭제
    private List<Book> book;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Restaurant> restaurants;
}
