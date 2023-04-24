package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.common.OwnerAdmit;
import com.zerobase.babbook.domain.form.BookForm;
import com.zerobase.babbook.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 예약 관련 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {


    private final String TOKEN = "X-AUTH-TOKEN";
    private final BookService bookService;

    /**
     * 예약 내역 상세 조회 API
     */
    @GetMapping("/find/{book_id}")
    public ResponseEntity<?> getBookDetail(@PathVariable(name = "book_id") Long bookId) {
        return ResponseEntity.ok().body(bookService.getBookDetail(bookId));
    }

    /**
     * 내가 보낸 예약 내역 전체 리스트 출력 API
     */
    @GetMapping("/list")
    public ResponseEntity<?> myBookList(@RequestHeader(name = TOKEN) String token) {
        return ResponseEntity.ok().body(bookService.mybookList(token));
    }

    /**
     * 유저 -> 점주 예약 요청 전송(request) API
     */
    @PostMapping("/request/{restaurant_id}")
    public ResponseEntity<?> requestBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId,
        @RequestBody BookForm form) {
        return ResponseEntity.ok().body(bookService.requestBook(token, restaurantId, form));
    }

    /**
     * 점주 -> 유저 예약 응답 전송(response) API
     */
    @PostMapping("/response/{book_id}")
    public ResponseEntity<?> getBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "book_id") Long bookId, @RequestBody OwnerAdmit ownerAdmit) {
        return ResponseEntity.ok().body(bookService.responseBook(token, bookId, ownerAdmit));
    }

    /**
     * 유저가 스스로 예약 취소 API
     */
    @PostMapping("/cancel/{book_id}")
    public ResponseEntity<?> cancelBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "book_id") Long bookId) {
        return ResponseEntity.ok().body(bookService.cancelBook(token, bookId));
    }
}