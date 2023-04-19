//package com.zerobase.babbook.service;
//
//import com.zerobase.babbook.config.CommonDetails;
//import com.zerobase.babbook.domain.entity.Book;
//import com.zerobase.babbook.domain.entity.Restaurant;
//import com.zerobase.babbook.domain.reprository.BookRepository;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import javax.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class BookService {
//    private final BookRepository bookRepository;
//    @Transactional
//    public void book(Book reservations, Long rid, CommonDetails commonDetails) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setId(rid);
//        reservations.setRestaurant(restaurant);
//        reservations.setUser(commonDetails.getUser());
//        bookRepository.save(reservations);
//    }
//
//    public List<Book> findByUserid(Long userId) {
//        return bookRepository.findByUser_id(userId);
//    }
//
//    public Page<Book> findByUserid(Pageable pageable, Long uid) {
//        Page<Book> lists = bookRepository.findByUser_id(pageable, uid);
//        return lists;
//    }
//
//    public Book findBook(Long bookId) {
//        return bookRepository.findById(bookId).get();
//    }
//
//    @Transactional
//    public void updateBook(Book b) throws ParseException {
//        Book book = bookRepository.findById(b.getId()).get();
//        bookRepository.save(book);
//    }
//
//    public void cancelBook(Long bookId) {
//        bookRepository.deleteById(bookId);
//    }
//}
