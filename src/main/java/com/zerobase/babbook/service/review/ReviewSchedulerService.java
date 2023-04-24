package com.zerobase.babbook.service.review;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.reprository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 예약 중 이미 사용된 예약을 찾고,
 * 해당 예약의 예약일로부터 7일이 지났으면 자동으로 예약이 불가능한 상태로 업데이트 하는 클래스
 * 매일 0시 0분 0초에 해당 스케쥴로가 실행됨.
 */
@Service
public class ReviewSchedulerService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 0분 0초에 실행
    public void checkReviewableBooks() {
        LocalDateTime now = LocalDateTime.now();
        List<Book> books = bookRepository.findByStatusCode(StatusCode.USED_BOOK);
        for (Book book : books) {
            if (now.isAfter(book.getCreatedBookTime().plusDays(7))) {
                book.setStatusCode(StatusCode.REVIEW_WRITE_OVER);
            }
        }
    }

}