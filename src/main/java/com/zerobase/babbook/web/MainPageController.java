package com.zerobase.babbook.web;

import com.zerobase.babbook.domain.entity.User;
import com.zerobase.babbook.domain.reprository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //기본화면으로 login, logout, 검색, login 후에는 예약 상태 확인 여부
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/main")
    public String getMain() {
        return "main";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "loginPage";
    }

    @GetMapping("/signUp")
    public String signUp() {
        User user = User.builder()
            .email("galid")
            .password(passwordEncoder.encode("1234"))
            .phone("010-1111-2222")
            .role("user")
            .build();

        userRepository.save(user);

        return "redirect:/login";
    }
}
