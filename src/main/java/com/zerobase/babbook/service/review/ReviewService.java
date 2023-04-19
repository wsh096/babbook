//package com.zerobase.babbook.service;
//
//import com.zerobase.babbook.domain.entity.Review;
//import com.zerobase.babbook.domain.reprository.ReviewRepository;
//import java.util.List;
//import java.util.UUID;
//import javax.servlet.http.HttpSession;
//import javax.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ReviewService {
//    ReviewRepository reviewRepository;
//    @Transactional
//    public void review(Review reviews, HttpSession session) {
//        UUID uuid = UUID.randomUUID();
//        reviewRepository.save(reviews);
//    }
//
//    // 리뷰 삭제
//    public void reviewDelete(Long id) {
//        reviewRepository.deleteById(id);
//    }
//
//    // 내 리뷰 보기
//    public Page<Review> findByUserReview(Pageable pageable,Long user_id) {
//        return reviewRepository.findByUser_id(user_id,pageable);
//    }
//}
