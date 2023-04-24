package com.zerobase.babbook.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
/**
 * kiosk에 관한 엔티티로 book과 다대일 관계.
 * 해당 값이 실제 사용할 때 조회하는 지연 로딩 사용.
 * */
@Getter
@Setter
@Entity
@Table(name = "kiosk")
public class Kiosk extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Book book;
}
