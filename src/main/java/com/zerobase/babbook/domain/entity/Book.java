package com.zerobase.babbook.domain.entity;

import com.zerobase.babbook.domain.common.StatusCode;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 예약 테이블 관련한 속성들이 있으며
 * 주요 사항 5가지만 기입
 * @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
 * private List<Kiosk> kiosks;
 * 1. cascade = CascadeType.REMOVE 삭제를 통해, db 관련한 처리 오류 발생을 막아줌.
 * 2. fetch = FetchType.LAZY 해당 엔티티와 관계를 맺은 엔티티가 실제로 사용되기 전까지는 로딩을 지연(실제 사용 때 조회)
 *
 * 3. @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 *  DateTimeFormat 지정하는 어노테이션
 *
 * 4. @Enumerated(EnumType.STRING)
 *  해당 변수가 Enum 형태임을 나타내고, DB 에 Enum '문자열 형태로' 저장
 * 5. LocalDateTime DeadLineTime 예약을 생성하면 자동으로 10분 전 값이 생성.
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private Restaurant restaurant;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Kiosk> kiosks;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdBookTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadLineTime;
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;


}
