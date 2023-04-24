package com.zerobase.babbook.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * review 엔티티 클래스의 review 를 추가할 때 사용하는 form
 * 리뷰 제목, 설명, 별점을 줌 이때 값이 int로 들어가지만 변환해 0~5점 사이의 0.5점 단위의 값이 들어가며
 * 이외의 값은 유효성 검사로 처리됨.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewForm {
    private String title;
    private String description;
    private int rate;
}
