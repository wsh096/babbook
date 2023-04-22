package com.zerobase.babbook.service.book;

import static com.zerobase.babbook.domain.common.BookCode.AUTO_CANCEL_BOOK;

import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.reprository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Scheduled 어노테이션을 통해 주기적으로 실행되는 클래스.
@Service
public class BookSchedulerService {

    private final BookRepository bookRepository;

    @Autowired
    public BookSchedulerService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    // 5분 정도의 간격으로 실행
    // 10분의 간격은 각 예약 별 시간의 편차가 생길 것으로 추정
    @Scheduled(fixedRate = 300000)
    public void updateBookCode() {
        LocalDateTime now = LocalDateTime.now();
        List<Book> books = bookRepository.findByDeadLineTimeBeforeAndBookCodeNot(now, AUTO_CANCEL_BOOK);
        for (Book book : books) {
            book.setBookCode(AUTO_CANCEL_BOOK);
            bookRepository.save(book);
        }
    }
}