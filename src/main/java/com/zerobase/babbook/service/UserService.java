package com.zerobase.babbook.service;

import com.zerobase.babbook.config.CommonDetails;
import com.zerobase.babbook.domain.entity.Book;
import com.zerobase.babbook.domain.entity.Restaurant;
import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.reprository.BookRepository;
import com.zerobase.babbook.domain.reprository.ReviewRepository;
import com.zerobase.babbook.domain.reprository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;//입력된 암호를 암호화//

    @Transactional
    public String signIn(User user) {//회원 가입
        if (userRepository.findByUserMail(user.getUserMail()) != null) {
            return "fail";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "success";
    }
    @Transactional
    public void update(User user) {// 업데이트
        User u = userRepository.findById(user.getId()).get();
        u.setPassword(user.getPassword());
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //u.setPhone(user.getPhone()); 번호의 경우, 인증 관련해서 좀 더 고려해 보기.
    }
    @Transactional
    public void delete(Long id) {//회원 탈퇴
        userRepository.deleteById(id);
    }
    @Transactional// 예약에 관한 부분
    public void book(Book book, Long bookId, CommonDetails user) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(bookId);
        book.setRestaurant(restaurant);
        book.setUser(user.getUser());
        bookRepository.save(book);
    }
}
