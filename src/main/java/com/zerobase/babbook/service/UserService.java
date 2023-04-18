//package com.zerobase.babbook.service;
//
//import com.zerobase.babbook.domain.common.UserRole;
//import com.zerobase.babbook.domain.entity.User;
//import com.zerobase.babbook.domain.reprository.BookRepository;
//import com.zerobase.babbook.domain.reprository.ReviewRepository;
//import com.zerobase.babbook.domain.reprository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private static final UserRole USER = UserRole.USER;
//    private static final UserRole OWNER = UserRole.OWNER;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;//입력된 암호를 암호화//
//
//    @Transactional
//    public String join(User user) {
//        if (userRepository.findByUserMail(user.getEmail()) != null) {
//            return "fail";
//        }
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "success";
//    }
//
//    @Transactional
//    public void update(User user) {// 업데이트
//        User u = userRepository.findById(user.getId()).get();
//        u.setPassword(user.getPassword());
//        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//    }
//
//    @Transactional
//    public void delete(Long id) {//회원 탈퇴
//        userRepository.deleteById(id);
//    }
//
//}
