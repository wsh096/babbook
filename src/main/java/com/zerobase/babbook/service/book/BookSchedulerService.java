package com.zerobase.babbook.service.book;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.reprository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 일정 주기 마다 실행하는 클래스로,
 * 예약 데드라인을 넘기면 자동 취소되는 것을
 * 5분 단위로 확인하는 클래스
 *
 * 특히 예약 중인 값, 즉 점주가 승인한 값만 가져오는 stream 식으로 book 을 가져와 값을 확인.
 */

@Service
public class BookSchedulerService {

    private final BookRepository bookRepository;

    @Autowired
    public BookSchedulerService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 300000)//5분마다 확인
    public void updateBookCode() {
        LocalDateTime now = LocalDateTime.now();
        List<Book> books = bookRepository.findByDeadLineTimeBeforeAndStatusCodeNot(now,
                StatusCode.TIMEOUT_CANCEL_BOOK)
            .stream().filter(book ->
                book.getStatusCode().equals(StatusCode.OWNER_ACCEPT_BOOK))
            .collect(Collectors.toList());
        for (Book book : books) {
            book.setStatusCode(StatusCode.TIMEOUT_CANCEL_BOOK);
            bookRepository.save(book);
        }
    }
}