package com.fastcampus.mvcboardproject.controller;

//import jakarta.validation.Valid;
import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.dto.request.UserAccountRequest;
import com.fastcampus.mvcboardproject.dto.security.BoardPrincipal;
import com.fastcampus.mvcboardproject.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "login";
    }

    // [GET] 회원가입 페이지
    @GetMapping("/sign-up")
    public String RegistrationForm(ModelMap model){
        return "sign-up";
    }

    // [POST] 회원가입 정보 저장
    @PostMapping("/singn-up/save")
    public String Registration(@Valid @ModelAttribute("user") UserAccountDto user,
                               @AuthenticationPrincipal BoardPrincipal boardPrincipal,
                               UserAccountRequest userAccountRequest
    ){
        log.error("[MainController] 유저 : {}, 권한 : {}, 요청: {}", user, boardPrincipal, userAccountRequest);
        userAccountService.saveUser(user);
        return "redirect:/login?success";
    }

}
