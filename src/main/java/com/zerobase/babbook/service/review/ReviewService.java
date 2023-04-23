package com.zerobase.babbook.service.review;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.dto.ReviewDto;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.Review;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.ReviewForm;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.ReviewRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtAuthenticationProvider provider;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //조회
    public List<ReviewDto> MyReviewList(String token) {
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ReviewDto.fromList(reviewRepository.findByBook_User(user));
    }

    public ReviewDto getReviewDetail(String token,Long reviewId) {
        return ReviewDto.from(reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW)));
    }
    @Transactional
    public String addReview(String token, ReviewForm form, long bookId) {
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_BOOK)
        );
        //book 의 user와 매치되지 않으면 확인할 필요 없음
        if (user != book.getUser()) {
            throw new CustomException(ErrorCode.NO_MATCH_BOOK_AND_USER);
        }
        //작성 가능한 내역인지 확인
        if (book.getStatusCode() != StatusCode.USED_BOOK) {
            if (book.getStatusCode() == StatusCode.REVIEW_WRITE_OVER) {
                throw new CustomException(ErrorCode.TIME_OVER_WRITE_REVIEW);
            }
            if (book.getStatusCode() == StatusCode.COMPLETE_REVIEW) {
                throw new CustomException(ErrorCode.ALREADY_WRITE_REVIEW);
            }
            throw new CustomException(ErrorCode.NO_ALLOW_WRITE_REVIEW);
        }
        //점수가 제대로 된 입력값인지 확인
        double rate = form.getRate() / (double) 2;
        if (rate < 0D || rate > 5D) {// 입력값을 0.5 단위로 변환
            throw new CustomException(ErrorCode.NOT_ALLOW_POINT);
        }

        book.setStatusCode(StatusCode.COMPLETE_REVIEW);
        bookRepository.save(book);//작성 완료되게 만든 이후 해당 book 이 저장되게 하기.

        //작성된 리뷰 저장
        Review review = Review.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .rate(rate)
            .book(book)
            .build();
        reviewRepository.save(review);
        return "리뷰 작성이 완료 되었습니다.";
    }



//삭제에 관한 부분 활용할지 아직 몰라 주석
//    @Transactional
//    public void deleteReview(Long reviewId) {
//        Review review = reviewRepository.findById(reviewId)
//            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
//        reviewRepository.delete(review);
//    }
}
