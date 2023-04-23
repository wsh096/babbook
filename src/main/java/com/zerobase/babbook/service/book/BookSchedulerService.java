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
    // 추가로 모든 BookCode를 가지고 올 필요가 없고, 예약 중인 것들만 가지고 오면 되기 때문에 스트림식 작성.
    @Transactional
    @Scheduled(fixedRate = 300000)
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