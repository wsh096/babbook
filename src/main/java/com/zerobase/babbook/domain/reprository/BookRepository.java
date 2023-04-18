package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByUser_id(Long user_id);

    public Page<Book> findByUser_id(Pageable pageable, Long user_id);
}
