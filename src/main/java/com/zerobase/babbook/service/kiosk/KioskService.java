package com.zerobase.babbook.service.kiosk;

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
/**
 * 키오스크에 관한 비즈니스 로직 담당하는 서비스 클래스
 * 사용자 확인과 예약 확인, 토큰의 검증이 주요 사용
 */
@Service
@RequiredArgsConstructor
public class KioskService {

    private final JwtAuthenticationProvider provider;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    /**
     * 예약 확인 메서드.
     */
    @Transactional
    public String checkBook(String token, Long bookId) {
        /**
         * Token 유효성 검사
         */
        if(!provider.validateToken(token)){
            throw new CustomException(ErrorCode.DO_NOT_RIGHT_TOKEN);
        }
        UserDto userDto = provider.getUserDto(token);
        /**
         * 유저 존재 확인
         */
        User user = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        /**
         * 예약 존재 확인
         */
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOOK));
        if (book.getUser() != user) {
            throw new CustomException(ErrorCode.NO_MATCH_BOOK_AND_USER);
        }
        /**
         * 상태 코드 확인
         * 예약 중인 상태가 아닌 경우 경우에 따라 에러 코드 출력
         */
        if (book.getStatusCode() != StatusCode.OWNER_ACCEPT_BOOK) {
            /**
             * 승인이 되지 않은 예약건
             */
            if (book.getStatusCode() == StatusCode.USER_WAIT_BOOK) {
                throw new CustomException(ErrorCode.NO_ACCEPT_BOOK);

            }
            /**
             * 이미 취소 되었거나 사용된 예약건
             */
            else {
                throw new CustomException(ErrorCode.NO_USE_BOOK);
            }
        }
        /**
         * 현재 시간보다, 예약 확인 시간이 뒤에 있는 경우.
         * 즉 현재시간 1시 35분 11초, deadLineTime 1시 49분 11초
         * 사용한 예약으로 처리. 이외의 경우, 예약이 처리되지 않았다고 처리
         */
        LocalDateTime nowTime = LocalDateTime.now();
        if (book.getDeadLineTime().isAfter(nowTime)) {

            book.setStatusCode(StatusCode.USED_BOOK);
            bookRepository.save(book);
            return "예약이 정상적으로 승인되었습니다.";
        }
        return "예약이 처리되지 않았습니다.";
    }
}
