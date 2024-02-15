package com.fastcampus.mvcboardproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 홈버튼은 게시판 페이지로 리다이렉션
    @GetMapping("/")
    public String root() {
        return "forward:/articles";
    }
}
