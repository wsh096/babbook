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
/**
 * 리뷰에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtAuthenticationProvider provider;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /**
     * 예약자가 자신의 리뷰를 조회
     * 자신의 토큰으로 조회가 가능
     * 유효성 검증과 유저 존재 확인
     * Dto 를 통해 fromList 메서드로 개괄적인 정보만 가져옴.
     * 제목과 별점
     */
    //조회
    public List<ReviewDto> MyReviewList(String token) {
        if (!provider.validateToken(token)) {
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ReviewDto.fromList(reviewRepository.findByBook_User(user));
    }
    /**
     * 예약자가 자신의 리뷰를 상세하게 확인
     * 자신의 토큰으로 조회가 가능
     * 유효성 검증과 유저 존재 확인
     * Dto 를 통해 from 메서드로 상세 정보 조회.
     * 제목, 내용, 별점.
     */
    public ReviewDto getReviewDetail(String token, Long reviewId) {
        if (!provider.validateToken(token)) {
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        return ReviewDto.from(reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW)));
    }

    /**
     * 리뷰 저장
     */
    @Transactional
    public String addReview(String token, ReviewForm form, long bookId) {
        /**
         * 토큰 유효성 검사, 유저 확인, book확인
         */
        if (!provider.validateToken(token)) {
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Book book = bookRepository.findById(bookId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_BOOK));
        /**
         * 유저와 book 매치 여부 확인
         * 해당 내역에서 유저와 book 매치를 확인했기 때문에 조회는 확인 안함.
         */
        if (user != book.getUser()) {
            throw new CustomException(ErrorCode.NO_MATCH_BOOK_AND_USER);
        }

        /**
         * 리뷰가 작성 가능한지 확인(StatusCode)
         * 사용한 예약이 아닌 경우 나누어서 확인
         */
        if (book.getStatusCode() != StatusCode.USED_BOOK) {
            /**
             * 작성 기한이 지난 경우.
             */
            if (book.getStatusCode() == StatusCode.REVIEW_WRITE_OVER) {
                throw new CustomException(ErrorCode.TIME_OVER_WRITE_REVIEW);
            }
            /**
             * 이미 작성한 경우
             */
            if (book.getStatusCode() == StatusCode.COMPLETE_REVIEW) {
                throw new CustomException(ErrorCode.ALREADY_WRITE_REVIEW);
            }
            /**
             * 그 외의 경우.
             * 아직 사용하지 않은 예약 또는 이미 취소한 예약(하나로 묶어둠)
             */
            throw new CustomException(ErrorCode.NO_ALLOW_WRITE_REVIEW);
        }
        /**
         * 별점의 유효성 검사.
         */
        double rate = form.getRate() / (double) 2;
        if (rate < 0D || rate > 5D) {// 입력값을 0.5 단위로 변환
            throw new CustomException(ErrorCode.NOT_ALLOW_POINT);
        }
        /**
         * 리뷰 작성이 가능한 상태이므로 리뷰 작성 완료로 book 을 바꾸고 저장.
         */
        book.setStatusCode(StatusCode.COMPLETE_REVIEW);
        bookRepository.save(book);//작성 완료되게 만든 이후 해당 book 이 저장되게 하기.

        /**
         * 작성된 리뷰 저장 후 문자열 반환
         */
        Review review = Review.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .rate(rate)
            .book(book)
            .build();
        reviewRepository.save(review);
        return "리뷰 작성이 완료 되었습니다.";
    }
}
