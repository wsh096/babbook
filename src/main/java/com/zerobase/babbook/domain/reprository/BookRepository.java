package com.zerobase.babbook.domain.reprository;

import com.zerobase.babbook.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
