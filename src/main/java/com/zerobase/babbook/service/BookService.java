package com.zerobase.babbook.service;

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
        String name = restaurant.getName();
        return name + " 으로 예약 요청이 정상적으로 전송되었습니다.";
    }

    public String responseBook(String token, Long bookId, OwnerAdmit ownerAdmit) {
        UserDto ownerCheck = provider.getUserDto(token);
        Owner owner = ownerRepository.findById(ownerCheck.getId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_BOOK));

        if (ownerAdmit.getCode().equals(null)) {
            throw new CustomException(DO_NOT_CORRECT_BOOK_RESPONSE);
        }else if(ownerAdmit.getCode().equals(ACCEPT)){
            accept(book.getId());
        }else{//REJECT

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

    private Book accept(Long bookId) {
        return
    }

    ;

    private void reject() {
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
