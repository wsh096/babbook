package com.zerobase.babbook.domain.form;

import com.zerobase.babbook.domain.entity.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * book 엔티티 클래스의 시간을 생성할때 사용하는 form
 * bookTime 으로, 예약 시간 문자열로 저장
 *
 * 이를 통해 서비스에서, 값을 만들 때, formarting 한 적절한 값으로 변하고 적절하지 않은 값이 오면 에러 발생
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookForm {
    String bookTime;
}
