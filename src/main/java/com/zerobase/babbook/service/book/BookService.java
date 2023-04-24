package com.zerobase.babbook.service.book;

import com.zerobase.babbook.domain.common.OwnerAdmit;
import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.dto.BookDto;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.Owner;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.form.BookForm;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.OwnerRepository;
import com.zerobase.babbook.domain.reprository.RestaurantRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * 예약에 관한 비즈니스 로직 담당하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final JwtAuthenticationProvider provider;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * 나의 예약 내역을 심플하게 List 로
     * 토큰 유효성 검사 후, 유저 존재 확인 후
     * BookDto의 myBookList를 실행시켜 간단하게 값 가져옴.
     */
    public List<BookDto> mybookList(String token) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository
            .findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return BookDto.myBookList(user.getBook());
    }
    /**
     * 예약 내역 상세 조회. 예약 번호로 확인
     */
    public BookDto getBookDetail(Long bookId) {
        return BookDto.from(bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOK)));
    }

    /**
     * 유저의 예약생성
     * 점주쪽으로 예약 승인 요청을 보내는 형태.
     * 이때 토큰 검사, 유저 검사, 시간값 format에 맞게 유효성 검사. 후
     * 포맷은 맞지만, 만약 현재보다 이전 시간인 경우.
     * 현재 시간보다 30분 이전인 경우. 너무 빠른 예약은 할 수 없음.
     * (하지 않은 것 중 너무 먼 미래를 하는 것도 예외 처리 할 수 있음.)
     * 현재시간 보다 3시간을 더한 값보다 더 큰값과 같은 형태.(정책을 별도로 정하지 않아 구현은 안함.)
     *
     * 끝으로 book을 만들고, 해당 식당으로 요청을 보냈다는 메시지 반환
     */
    @Transactional
    public String requestBook(String token, Long restaurantId, BookForm form) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userCheck = provider.getUserDto(token);

        User user = userRepository.findById(userCheck.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        /**
         *없는 식당인 경우
         */
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT));

        //시간값 형식이 바르지 않은 경우
        if (!isValidateBookTime(form.getBookTime())) {
            throw new CustomException(ErrorCode.DO_NOT_VALID_TIME);
        }

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime bookTime = LocalDateTime.parse(form.getBookTime(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        //fast fail
        /**
         * * 포맷은 맞지만, 만약 현재보다 이전 시간인 경우.
         */
        if (bookTime.isBefore(nowTime)) {
            throw new CustomException(ErrorCode.ALREADY_OVER_TIME);
        }
        /**
         * 현재 시간보다 30분 이전인 경우. 너무 빠른 예약은 할 수 없음.
         * 30분을 기본값으로 가정. 10분 이전의 예약 시간이기 때문에 해당 시간과 같은 설정을 넣음.
         */
        if (bookTime.isBefore(
            nowTime.plusMinutes(30))) {
            throw new CustomException(ErrorCode.TOO_EARLY_BOOK_TIME);
        }

        Book book = Book.builder()
            .createdBookTime(bookTime)
            .deadLineTime(bookTime.minusMinutes(10))
            .user(user)
            .restaurant(restaurant)
            .statusCode(StatusCode.USER_WAIT_BOOK)
            .build();
        bookRepository.save(book);
        String name = restaurant.getName();
        return name + " 으로 예약 요청이 정상적으로 전송되었습니다.";
    }

    /**
     * 시간의 형식의 유효성 검사.
     */
    private boolean isValidateBookTime(String bookTime) {
        try {
            LocalDateTime.parse(bookTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 점주의 승인 절차.
     * OwnerAdmit의  "승인" , "거절"로 값을 입력해 statusCode 를 바꿔줌.
     */
    @Transactional
    public String responseBook(String token, Long bookId, OwnerAdmit ownerAdmit) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto ownerCheck = provider.getUserDto(token);
        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_OWNER));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOK));

        /**
         * DB 상의 매칭 오류일 수 있는 부분.
         * Restaurant 정보가 일치하지 않는 경우
         */
        if (!owner.getRestaurant().contains(book.getRestaurant())) {
            //해당 경우 자동으로 취소되게 응답 보냄과 동시에, 에러 코드 발생.
            book.setStatusCode(StatusCode.AUTO_CANCEL_BOOK);
            bookRepository.save(book);
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_BOOK_RESPONSE);
        }
        //대기 중인 고객만 아래의 값을 받을 수 있음.
        if (book.getStatusCode() != StatusCode.USER_WAIT_BOOK) {
            throw new CustomException(ErrorCode.DO_NOT_WAIT_REQUEST);
        }
        /**
         * OwnerAdmit에 따라 값을 다르게 함.
         */
        if (ownerAdmit == OwnerAdmit.ACCEPT) {
            acceptResponse(book);
            return "성공적으로 승인되었습니다.";
        } else if (ownerAdmit == OwnerAdmit.REJECT) {//REJECT
            rejectResponse(book);
            return "예약이 거절 되었습니다.";
        } else {
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_BOOK_RESPONSE);
        }
    }

    /**
     * 예약을 승인한 경우
     */
    private void acceptResponse(Book book) {
        book.setStatusCode(StatusCode.OWNER_ACCEPT_BOOK);//예약 중인 상태.
        bookRepository.save(book);
    }
    /**
     * 예약을 거절한 경우
     */
    private void rejectResponse(Book book) {
        book.setStatusCode(StatusCode.OWNER_REJECT_BOOK);
        bookRepository.save(book);
    }

    /**
     * 예약을 취소하는 경우.
     */
    @Transactional
    public String cancelBook(String token, Long bookId) {
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userDto = provider.getUserDto(token);
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOK));

        //이미 취소 된 예약번호는 취소할 수 없음
        if (book.getStatusCode() == StatusCode.TIMEOUT_CANCEL_BOOK ||
            book.getStatusCode() == StatusCode.AUTO_CANCEL_BOOK) {
            throw new CustomException(ErrorCode.ALREADY_CANCEL_BOOK);
        }
        //예약자와 book 의 유저의 일치 여부 확인
        if (!user.equals(book.getUser())) {
            throw new CustomException(ErrorCode.DO_NOT_CORRECT_BOOK_CANCEL);
        }
        //이제 취소가 가능한 상태.
        book.setStatusCode(StatusCode.USER_CANCEL_BOOK);
        bookRepository.save(book);
        return "예약이 정상적으로 취소되었습니다.";
    }


}
