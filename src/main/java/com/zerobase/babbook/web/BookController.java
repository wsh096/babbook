package com.zerobase.babbook.web;

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

    //예약 상태 확인
    private final String TOKEN = "X-AUTH-TOKEN";
    private final BookService bookService;

    //예약 내역 확인
    @PostMapping("/send/{restaurant_id}")
    public ResponseEntity<?> getBook(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "restaurant_id") Long restaurantId,
        @RequestBody BookForm form) {
        return ResponseEntity.ok().body(bookService.sendBook(token, restaurantId, form));
    }
}