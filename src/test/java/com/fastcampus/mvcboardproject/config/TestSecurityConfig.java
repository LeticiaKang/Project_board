package com.fastcampus.mvcboardproject.config;

import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/*
    테스트 전용 Security Config
 */
@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    /*
        테스트용 유저 계정을 만들어,테스트에서 사용한다.
        @BeforeTestMethod : spring boot 메서드 test를 하기 전에 해당 메서드를 실행
     */
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                        "unoTest",
                        "pw",
                        "uno-test@email.com",
                        "uno-test",
                        "test memo")));
    }

}
