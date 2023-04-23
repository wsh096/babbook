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
            .deadLineTime(book.getDeadLineTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))//해당 시간이 예약 결정의 판가름
            .statusCode(book.getStatusCode())
            .build();
    }

    //리스트 페이지는 적은 데이터만 가져오면 되기 때문에 null 값이 들어가는 형태가 되긴 하지만 List 로 만들어 구현
    public static List<BookDto> myBookList(List<Book> books) {

        List<BookDto> myBookList = new ArrayList<>();

        //우선 최대 1000개까지만 너무 많은 데이터가 들어가지 않게
        // 이후 페이지 처리 부분 만들어 해결하기.
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
