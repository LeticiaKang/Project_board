package com.fastcampus.mvcboardproject.controller;

import com.fastcampus.mvcboardproject.dto.response.UserAccountResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

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

//    // [GET] 회원가입 페이지
//    @GetMapping("singn-up")
//    public String showRegistrationForm(ModelMap map){
//        UserAccountResponse user = new UserAccountResponse();
//        map.addAttribute("user", user);
//        return "singn-up";
//    }

//    // [POST] 회원가입 정보 저장
//    @PostMapping("/singn-up/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto user,
//                               BindingResult result,
//                               ModelMap map){
//        UserAccount existing = userService.findByEmail(user.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            map.addAttribute("user", user);
//            return "singn-up";
//        }
//        userService.saveUser(user);
//        return "redirect:/singn-up?success";
//    }
    
}
