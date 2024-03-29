package com.fastcampus.mvcboardproject.config;

import com.fastcampus.mvcboardproject.dto.security.BoardPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Optional;

@Slf4j
@EnableJpaAuditing  // audit활성화
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)  // 로그인한 상태인지 확인
                .map(Authentication::getPrincipal)  // getPrincipal : return Object
//                .map(BoardPrincipal.class::cast)    // Type Casting
//                .map(BoardPrincipal::getUsername)
                .map(principal -> {
                    if (principal instanceof BoardPrincipal) {
                        return ((BoardPrincipal) principal).getUsername();
                    }
                    return "defaultUser";
                });
    }
}
