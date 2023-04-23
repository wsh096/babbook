package com.zerobase.babbook.web;

import com.zerobase.babbook.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {
    private final String TOKEN = "X-AUTH-TOKEN";
    private final KioskService kioskService;

    @PostMapping("/check/{book_id}")
    public ResponseEntity<?> check(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name="book_id") Long bookId) {
        return ResponseEntity.ok(kioskService.checkBook(token, bookId));
    }

}
