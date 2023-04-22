package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.common.OwnerAdmit;
import com.zerobase.babbook.domain.form.BookForm;
import com.zerobase.babbook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    //예약 요청 전송(request)
    @PostMapping("/request/{restaurant_id}")
    public ResponseEntity<?> requestBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId,
        @RequestBody BookForm form) {
        return ResponseEntity.ok().body(bookService.requestBook(token, restaurantId, form));
    }
    //예약 요청 응답(response)
    //owner의 token으로 확인(owner 토큰인지는 service에서 확인,
    // (아무나 처리 가능하면 안 되기 때문.)
    @PostMapping("/response/{book_id}")
    public ResponseEntity<?> getBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "book_id") Long bookId, @RequestBody OwnerAdmit ownerAdmit) {
        return ResponseEntity.ok().body(token,bookService.responseBook(token,bookId,ownerAdmit));
    }
    //예약 전송
    @PostMapping("/send/{restaurant_id}")
    public ResponseEntity<?> getBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId,
        @RequestBody BookForm form) {
        return ResponseEntity.ok().body(bookService.sendBook(token, restaurantId, form));
    }
}