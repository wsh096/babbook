package com.zerobase.babbook.service.review;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.entity.Review;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.ReviewForm;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public Review addReview(String token, ReviewForm form, long bookId) {
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId()).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND_BOOK)
        );
        if (book.getStatusCode() != StatusCode.USED_BOOK) {
            if (book.getStatusCode() == StatusCode.REVIEW_WRITE_OVER) {
                throw new CustomException(ErrorCode.TIME_OVER_WRITE_REVIEW);
            }
            if (book.getStatusCode() == StatusCode.COMPLETE_REVIEW) {
                throw new CustomException(ErrorCode.ALREADY_WRITE_REVIEW);
            }
        }
        double rate = form.getRate() / (double) 2;
        if (rate < 0D || rate > 5D) {// 입력값을 0.5 단위로 변환
            throw new CustomException(ErrorCode.NOT_ALLOW_POINT);
        }

        Review review = Review.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .rate(rate)
            .book(book)
            .build();
        Restaurant restaurant = book.getRestaurant();
        restaurant.addReview(review);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewForm form) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        int rate = (int) (form.getRate() * 2);
        if (rate < 0 || rate > 10) {
            throw new CustomException(ErrorCode.NOT_ALLOW_POINT);
        }
        review.setTitle(form.getTitle());
        review.setDescription(form.getDescription());
        review.setRate(rate);
        review.updateRestaurantRating();
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        review.getRestaurant().removeReview(review);
        reviewRepository.delete(review);
    }

    @Transactional
    public List<Review> getMyReviews(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Review> reviews = reviewRepository.findByBook_User(user);
        return reviews;
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
    }

}
