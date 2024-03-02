package com.fastcampus.mvcboardproject.controller;

import com.fastcampus.mvcboardproject.dto.request.UserAccountRequest;
import com.fastcampus.mvcboardproject.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserAccountService userAccountService;

    // 홈버튼은 게시판 페이지로 리다이렉션
    @GetMapping("/")
    public String root() {
        return "forward:/articles";
    }

    // [GET] 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
    log.debug("[로그][MainController][loginForm] 로그인");
        return "login";
    }

    // [GET] 회원가입 페이지
    @GetMapping("/sign-up")
    public String RegistrationForm(){
        return "sign-up";
    }

    // [POST] 회원가입 정보 저장
    @PostMapping("/sign-up/save")
    public String Registration(
            UserAccountRequest userAccountRequest
    ) {
        log.debug("로그 [MainController] userAccountRequest: {}", userAccountRequest.toDto(userAccountRequest));
        userAccountService.saveUser(userAccountRequest.toDto(userAccountRequest));
        return "redirect:/";
    }

}
