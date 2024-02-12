package com.fastcampus.mvcboardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    // 생성자를 자동으로 넣기 위함
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("테스터_작성자");
        //TODO: 스프린 시큐리티로 인증 기능을 붙이게 될 때, 수정하기
    }
}
