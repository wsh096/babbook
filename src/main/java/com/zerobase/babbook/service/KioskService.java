package com.zerobase.babbook.service;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import com.zerobase.babbook.exception.CustomException;
import com.zerobase.babbook.exception.ErrorCode;
import com.zerobase.babbook.token.JwtAuthenticationProvider;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KioskService {

    private final JwtAuthenticationProvider provider;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public String checkBook(String token, Long bookId) {
        UserDto userDto = provider.getUserDto(token);

        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOK));
        if (book.getUser() != user) {
            throw new CustomException(ErrorCode.NO_MATCH_BOOK_AND_USER);
        }

        //예약 중인 상태가 아니라면 이를 관리할 필요가 없음
        if (book.getStatusCode() != StatusCode.OWNER_ACCEPT_BOOK) {
            if (book.getStatusCode() == StatusCode.USER_WAIT_BOOK) {
                throw new CustomException(ErrorCode.NO_ACCEPT_BOOK);
            } else {
                throw new CustomException(ErrorCode.NO_USE_BOOK);
            }
        }
        //승인이 가능한 경우. 현재시간과 데드라인 시간의 비교.
        //승인이 가능한 예약의 경우, USED_BOOK 으로 상태코드 바꿈.
        //book 에 저장.
        //해당 if문으로 값이 가지 않는 문제가 발생하고 있음.
        //해당 부분을 잡고 kiosk 처리 완료 예정.
        //isAfter가 맞음
        LocalDateTime nowTime = LocalDateTime.now();
        if (book.getDeadLineTime().isAfter(nowTime)) {

            book.setStatusCode(StatusCode.USED_BOOK);
            bookRepository.save(book);
            return "예약이 정상적으로 승인되었습니다.";
        }
        return "예약이 승인되지 않았습니다.";
    }
}
