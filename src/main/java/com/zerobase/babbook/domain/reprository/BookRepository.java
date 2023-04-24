package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.common.StatusCode;
import com.zerobase.babbook.domain.entity.Book;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *Book 엔티티를 처리하기 위한 Repository
 *
 * 데드라인 시간이 현재 시간보다 이전이고, 상태 코드가 특정 값이 아닌(= 취소되지 않은) 예약 리스트를 조회
 * 즉 데드라인 4시 59분, 현재시간 5시 (해당 서비스에서는 autoCancelBook 자동 취소 되었는지 확인하는데서만 쓰임)
 *
 * 특정 상태 코드를 가지는 예약 리스트 조회(해당 서비스에서는 usedBook 사용된 예약인지 확인하는데서만 쓰임)
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    //현재시간보다 이후인 데드라인시간인 경우, . 해당 예약은 자동으로 삭제
    List<Book> findByDeadLineTimeBeforeAndStatusCodeNot(LocalDateTime now, StatusCode statusCode);

    List<Book> findByStatusCode(StatusCode statusCode);

}
