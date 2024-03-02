package com.fastcampus.mvcboardproject.config;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.dto.security.BoardPrincipal;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
public class SecurityConfig {

    /*
        HTTP를 통한 인증과 인가를 담당하는 메서드이다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("[로그][SecurityConfig][securityFilterChain] 인증 확인");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag",
                                "/sign-up").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/sign-up/save",
                                "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃을 처리할 경로 지정
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션될 경로 지정
                        .invalidateHttpSession(true) // 로그아웃 시 HTTP 세션을 무효화
                        .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
                        .permitAll()
                );
        return http.build();
    }

    /*
    loadUserByUsername을 람다식으로 구현하였으면,
    입력값은 userAccountRepository이고
    반환값은 BoardPrincipal이다.
    userName을 찾을 수 없는 경우, NotFoundException 예외가 발생한다.
    */
    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        log.debug("[로그][SecurityConfig][userDetailsService] 인증 확인 : {}", userAccountRepository);
        return username ->
                userAccountRepository.findById(username)            // User 객체를 가여좀
                        .map(UserAccountDto::from)     // User 객체를 UserAccountDto로 변환함(작성, 수정 정보는 null)
                        .map(BoardPrincipal::from)     // 권한이 추가되어 BoardPrincipal객체로 변환
                        .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    /*
         spring security의 인증기능을 구현할 때, 비밀번호 암호화를 위해 반드시 설정해야 한다.
      */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("[로그][SecurityConfig][PasswordEncoder] 비밀번호 암호화");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
