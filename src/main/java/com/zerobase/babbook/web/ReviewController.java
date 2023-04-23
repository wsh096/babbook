package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.form.ReviewForm;
import com.zerobase.babbook.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final String TOKEN = "X-AUTH-TOKEN";
    private final ReviewService reviewService;

    //내가 리뷰한 목록 확인@GetMapping
    @GetMapping("/my")
    public ResponseEntity<?> getMyReview(@RequestHeader(name = TOKEN) String token) {
        return ResponseEntity.ok().body(reviewService.MyReviewList(token));
    }

    //내 리뷰 상세 확인@GetMapping
    @GetMapping("/my/{review_id}")
    public ResponseEntity<?> getMyReviewDetail(@RequestHeader(name = TOKEN) String token,
        @PathVariable(name = "review_id") Long reviewId) {
        return ResponseEntity.ok().body(reviewService.getReviewDetail(token, reviewId));
    }

    //리뷰 작성하기@PostMapping
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestHeader(name = TOKEN) String token,
        @RequestBody ReviewForm form,
        @RequestParam long bookId) {
        return ResponseEntity.ok().body(reviewService.addReview(token, form, bookId));
    }

}
