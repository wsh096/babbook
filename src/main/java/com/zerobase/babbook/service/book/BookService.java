package com.zerobase.babbook.service.book;

import static com.zerobase.babbook.domain.common.BookCode.AUTO_CANCEL_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.OWNER_ACCEPT_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.OWNER_REJECT_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.TIMEOUT_CANCEL_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.USER_CANCEL_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.USER_WAIT_BOOK;
import static com.zerobase.babbook.domain.common.OwnerAdmit.ACCEPT;
import static com.zerobase.babbook.domain.common.OwnerAdmit.REJECT;
import static com.zerobase.babbook.exception.ErrorCode.ALREADY_CANCEL_BOOK;
import static com.zerobase.babbook.exception.ErrorCode.BAD_ACCESS_TIME;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_ACCESS_BOOK_TIME;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_CORRECT_BOOK_CANCEL;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_CORRECT_BOOK_RESPONSE;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_WAIT_REQUEST;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_BOOK;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_OWNER;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_RESTAURANT;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.common.OwnerAdmit;
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
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final JwtAuthenticationProvider provider;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RestaurantRepository restaurantRepository;

    //예약생성
    public String requestBook(String token, Long restaurantId, BookForm form) {
        UserDto userCheck = provider.getUserDto(token);
        User user = userRepository.findById(userCheck.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime bookTime = LocalDateTime.parse(form.getBookTime(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        //fast fail
        if (bookTime.isBefore(nowTime)) {
            throw new CustomException(BAD_ACCESS_TIME);
        }
        //30분은 기본값. 10분 이전의 예약 시간이기 때문에 해당 시간과 같은 설정을 넣음.
        if (bookTime.isBefore(
            nowTime.plusMinutes(30))) {
            throw new CustomException(DO_NOT_ACCESS_BOOK_TIME);
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_RESTAURANT));
        Book book = Book.builder()
            .createdBookTime(bookTime)
            .deadLineTime(bookTime.minusMinutes(10))
            .user(user)
            .restaurant(restaurant)
            .bookCode(USER_WAIT_BOOK)
            .build();
        bookRepository.save(book);
        String name = restaurant.getName();
        return name + " 으로 예약 요청이 정상적으로 전송되었습니다.";
    }

    public String responseBook(String token, Long bookId, OwnerAdmit ownerAdmit) {
        UserDto ownerCheck = provider.getUserDto(token);
        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_BOOK));

        //DB 상의 매칭 오류일 수 있는 부분.
        // Restaurant 정보가 일치하지 않는 경우
        if (!owner.getRestaurant().contains(book.getRestaurant())) {
            //해당 경우 자동으로 취소되게 응답 보냄과 동시에, 에러 코드 발생.
            book.setBookCode(AUTO_CANCEL_BOOK);
            bookRepository.save(book);
            throw new CustomException(DO_NOT_CORRECT_BOOK_RESPONSE);
        }
        //대기 중인 고객만 아래의 값을 받을 수 있음.
        if(book.getBookCode()!=USER_WAIT_BOOK){
            throw new CustomException(DO_NOT_WAIT_REQUEST);
        }

         if (ownerAdmit == ACCEPT) {
            acceptResponse(book);
            return "성공적으로 승인되었습니다.";
        } else if (ownerAdmit == REJECT) {//REJECT
            rejectResponse(book);
            return "예약이 거절 되었습니다.";
        } else{
            throw new CustomException(DO_NOT_CORRECT_BOOK_RESPONSE);
        }
    }

    private void acceptResponse(Book book) {
        book.setBookCode(OWNER_ACCEPT_BOOK);//예약 중인 상태.
        bookRepository.save(book);
    }

    private void rejectResponse(Book book) {
        book.setBookCode(OWNER_REJECT_BOOK);
        bookRepository.save(book);
    }

    public String cancelBook(String token, Long bookId) {
        UserDto userCheck = provider.getUserDto(token);
        User user = userRepository.findById(userCheck.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_BOOK));

        //이미 취소 된 예약번호는 취소할 수 없음
        if (book.getBookCode().equals(TIMEOUT_CANCEL_BOOK) ||
            book.getBookCode().equals(AUTO_CANCEL_BOOK)) {
            throw new CustomException(ALREADY_CANCEL_BOOK);
        }
        //예약자와 book 의 유저의 일치 여부 확인
        if (!user.equals(book.getUser())) {
            throw new CustomException(DO_NOT_CORRECT_BOOK_CANCEL);
        }
        //이제 취소가 가능한 상태.
        book.setBookCode(USER_CANCEL_BOOK);
        bookRepository.save(book);
        return "예약이 정상적으로 취소되었습니다.";
    }

    //하나의 특정 예약의 정보를 확인.
    public Book getBookDetail(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_BOOK));
    }

    //유저 자신이 예약 내역 전체를 확인
    //심플하게 List를 간소화해서 보여주는 작업이 가능할 것으로 보임
    public List<Book> mybookList(String token) {
        UserDto userCheck = provider.getUserDto(token);
        User user = userRepository
            .findById(userCheck.getId()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return user.getBook();
    }
}
