package com.zerobase.babbook.web;

import com.zerobase.babbook.service.kiosk.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kiosk에 관한 컨트럴로로 checkBook()을 통해 book을 확인하고, 해당 예약의 처리를 POST로 진행
 */
@RestController
@RequestMapping("/kiosk")
@RequiredArgsConstructor
public class KioskController {
    private final String TOKEN = "X-AUTH-TOKEN";
    private final KioskService kioskService;

    /**
     * 토큰과 bookId를 받아 KioskService의 checkBook() 메서드를 호출하는 메서드
     *
     * @param token - RequestHeader의 X-AUTH-TOKEN으로 받은 JWT Token
     * @param bookId - PathVariable로 받은 Book ID
     * @return ResponseEntity
     */
    @PostMapping("/check/{book_id}")
    public ResponseEntity<?> check(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name="book_id") Long bookId) {
        return ResponseEntity.ok(kioskService.checkBook(token, bookId));
    }

}
