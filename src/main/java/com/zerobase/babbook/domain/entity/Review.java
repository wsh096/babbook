package com.zerobase.babbook.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 리뷰 테이블에 관한 엔티티 클래스 Book과는 1대1, Restaurant 및 User와는 다대일 연관관계 매핑
 * Review는 제목, 설명, 별점을 가집니다.
 * (0~5까지의 0.5점 단위로 단위로 0~10까지의 정수 값을 입력함에 따라 해당 형태로 변환합니다.)
 */
@Getter
@Setter
@Entity
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(length = 100)
    private String title;
    @Column(length = 1000)
    private String description;
    private double rate;

}
