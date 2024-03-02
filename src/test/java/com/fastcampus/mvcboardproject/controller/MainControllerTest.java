package com.fastcampus.mvcboardproject.controller;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.dto.request.UserAccountRequest;
import com.fastcampus.mvcboardproject.config.TestSecurityConfig;
import com.fastcampus.mvcboardproject.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(MainController.class)  // 테스트 대상을 작성하여 빈 스캐닝 최소화
@AutoConfigureMockMvc
class MainControllerTest {
    @Autowired
    private final MockMvc mvc;
    @MockBean
    private UserAccountService userAccountService;

    public MainControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[GET] [메인페이지]")
    @Test
    void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())                                 // 200 반환
                .andExpect(view().name("forward:/articles"))
                .andExpect(forwardedUrl("/articles"))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[GET] [회원가입 페이지]")
    @Test
    public void givenSignUpPage_whenAccessed_thenDisplaysSignUpForm() throws Exception {
        mvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("sign-up"));
    }

    @DisplayName("[POST] [회원가입 페이지]")
    @Test
    public void whenSignUpFormSubmitted_thenRedirectsToLoginPage() throws Exception {
        // Given
        UserAccountRequest userAccountRequest = new UserAccountRequest("testUser",  "password123", "test@example.com","password123", "testNickname", "Just a test");
        willDoNothing().given(userAccountService).saveUser(any(UserAccountDto.class));

        // When
        mvc.perform(post("/sign-up/save")
                        .param("userId", "testUser")
                        .param("userPassword", "password123")
                        .param("email", "test@example.com")
                        .param("re_pass", "password123")
                        .param("nickname", "testNickname")
                        .param("memo", "Just a test")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));

        // Then
        verify(userAccountService).saveUser(userAccountRequest.toDto(userAccountRequest));
    }

}