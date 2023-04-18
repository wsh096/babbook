package com.zerobase.babbook.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {
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
}
