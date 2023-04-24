package com.zerobase.babbook.domain.dto;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Long bookId: Book 엔티티의 식별자인 bookId String restaurantName: 예약이 이루어진 식당의 이름 String deadLineTime: 예약
 * 마감 시간 StatusCode statusCode: 예약의 상태를 나타내는 열거형 타입 StatusCode
 * <p>
 * BookDto 클래스 내의 메소드
 * <p>
 * public static BookDto from(Book book) BookDto 객체를 Book 엔티티에서 생성.
 * <p>
 * public static List<BookDto> myBookList(List<Book> books) BookDto 객체 리스트를 Book 엔티티 리스트에 생성.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long bookId;
    private String restaurantName;
    private String deadLineTime;
    private StatusCode statusCode;

    public static BookDto from(Book book) {

        return BookDto.builder()
            .bookId(book.getId())
            .restaurantName(book.getRestaurant().getName())
            .deadLineTime(
                book.getDeadLineTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .statusCode(book.getStatusCode())
            .build();
    }

    public static List<BookDto> myBookList(List<Book> books) {

        List<BookDto> myBookList = new ArrayList<>();

        int size = Math.min(1000, books.size());
        for (int i = 0; i < size; i++) {
            Book eachBook = books.get(i);
            BookDto eachBookDto = BookDto.builder()
                .restaurantName(eachBook.getRestaurant().getName())
                .statusCode(eachBook.getStatusCode())
                .build();
            myBookList.add(eachBookDto);
        }
        return myBookList;
    }
}
