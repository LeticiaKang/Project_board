package com.fastcampus.mvcboardproject.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testLoginProcess() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "uno")  // 'validUsername'을 유효한 사용자 이름으로 대체합니다.
                        .param("password", "asdf1234")  // 'validPassword'를 유효한 비밀번호로 대체합니다.
                        .with(csrf()))  // CSRF 토큰을 포함합니다.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));  // 로그인 성공 후 리다이렉션되는 URL을 검증합니다.
    }


}
