package com.zerobase.babbook.service.book;

import static com.zerobase.babbook.domain.common.BookCode.AUTO_CANCEL_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.OWNER_ACCEPT_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.OWNER_REJECT_BOOK;
import static com.zerobase.babbook.domain.common.BookCode.USER_WAIT_BOOK;
import static com.zerobase.babbook.domain.common.OwnerAdmit.ACCEPT;
import static com.zerobase.babbook.exception.ErrorCode.BAD_ACCESS_TIME;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_ACCESS_BOOK_TIME;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_CORRECT_ACCESS;
import static com.zerobase.babbook.exception.ErrorCode.DO_NOT_CORRECT_BOOK_RESPONSE;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_BOOK;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_OWNER;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_RESTAURANT;
import static com.zerobase.babbook.exception.ErrorCode.NOT_FOUND_USER;

import com.zerobase.babbook.domain.common.BookCode;
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
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
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

        //잘못된 요청의 경우
        if (ownerAdmit.getCode().equals(null)) {
            throw new CustomException(DO_NOT_CORRECT_BOOK_RESPONSE);
        } else if (ownerAdmit.getCode().equals(ACCEPT)) {
            acceptResponse(book);
            return "성공적으로 승인되었습니다.";
        } else {//REJECT
            rejectResponse(book);
            return "예약이 거절 되었습니다.";
        }
    }
    private void acceptResponse(Book book) {
        book.setBookCode(OWNER_ACCEPT_BOOK);
        bookRepository.save(book);
    }

    private void rejectResponse(Book book) {
        book.setBookCode(OWNER_REJECT_BOOK);
        bookRepository.save(book);
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
        String name = restaurant.getName();
        return name + " 으로 예약 요청이 정상적으로 전송되었습니다.";
    }



    public List<Book> findByUserid(Long userId) {
        return bookRepository.findByUser_id(userId);
    }

    public Page<Book> findByUserid(Pageable pageable, Long uid) {
        Page<Book> lists = bookRepository.findByUser_id(pageable, uid);
        return lists;
    }

    public Book findBook(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @Transactional
    public void updateBook(Book b) throws ParseException {
        Book book = bookRepository.findById(b.getId()).get();
        bookRepository.save(book);
    }

    public void cancelBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
