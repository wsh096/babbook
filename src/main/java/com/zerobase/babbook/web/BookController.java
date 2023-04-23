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

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {


    private final String TOKEN = "X-AUTH-TOKEN";
    private final BookService bookService;

    //유저의 예약 내역 확인
    // userController에 넣을지, 예약 내역에 넣어야 할지 참 애매한 상황.
    // common으로 따로 빼는 것도 고려(MainPage에 조회 검색, 유저의 예약 내역 확인과 같은 페이지 만들

    //예약 조회
    @GetMapping("/find/{book_id}")
    public ResponseEntity<?> getBookDetail(@PathVariable(name = "book_id") Long bookId) {
        return ResponseEntity.ok().body(bookService.getBookDetail(bookId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> myBookList(@RequestHeader(name = TOKEN) String token) {
        return ResponseEntity.ok().body(bookService.mybookList(token));
    }

    //예약 요청 전송(request)
    @PostMapping("/request/{restaurant_id}")
    public ResponseEntity<?> requestBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId,
        @RequestBody BookForm form) {
        return ResponseEntity.ok().body(bookService.requestBook(token, restaurantId, form));
    }

    //예약 요청 응답(response)
    //owner token 으로 확인_owner 토큰인지는 service 에서 확인
    // (아무나 처리 가능하면 안 되기 때문.)
    @PostMapping("/response/{book_id}")
    public ResponseEntity<?> getBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "book_id") Long bookId, @RequestBody OwnerAdmit ownerAdmit) {
        return ResponseEntity.ok().body(bookService.responseBook(token, bookId, ownerAdmit));
    }

    //유저의 예약 취소.USER_CANCEL_BOOK("유저 예약 취소"),
    @PostMapping("/cancel/{book_id}")
    public ResponseEntity<?> cancelBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "book_id") Long bookId) {
        return ResponseEntity.ok().body(bookService.cancelBook(token, bookId));
    }
}