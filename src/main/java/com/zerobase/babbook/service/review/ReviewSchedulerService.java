package com.zerobase.babbook.service.review;

import static com.zerobase.babbook.domain.common.StatusCode.REVIEW_WRITE_OVER;
import static com.zerobase.babbook.domain.common.StatusCode.USED_BOOK;

import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.reprository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReviewSchedulerService {

    @Autowired
    private BookRepository bookRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시 0분 0초에 실행
    public void checkReviewableBooks() {
        LocalDateTime now = LocalDateTime.now();
        List<Book> books = bookRepository.findByStatusCode(USED_BOOK);
        for (Book book : books) {
            if (now.isAfter(book.getCreatedBookTime().plusDays(7))) {
                book.setStatusCode(REVIEW_WRITE_OVER);
            }
        }
    }

}