package com.zerobase.babbook.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *  JPA에서 Entity 클래스들의 공통 속성 정의한 추상 클래스
 *  BaseEntity 클래스에는 createdAt, modifiedAt 두 개의 LocalDateTime 타입 변수가 선언
 * 각각 생성일자와 수정일자를 나타냄
 */
@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
