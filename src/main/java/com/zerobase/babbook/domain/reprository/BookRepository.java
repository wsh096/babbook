package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    //현재시간보다 이후인 데드라인시간인 경우, 즉 현재시간 5시 데드라인 4시 59분인 경우. 해당 예약은 자동으로 삭제
    List<Book> findByDeadLineTimeBeforeAndStatusCodeNot(LocalDateTime now, StatusCode autoCancelBook);

    List<Book> findByStatusCode(StatusCode usedBook);

}
