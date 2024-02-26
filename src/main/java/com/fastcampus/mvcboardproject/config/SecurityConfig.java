package com.fastcampus.mvcboardproject.config;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.dto.security.BoardPrincipal;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    /*
        HTTP를 통한 인증과 인가를 담당하는 메서드이다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(HttpMethod.GET, "/", "/articles", "/articles/search-hashtag").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(
                    form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/")
                            .permitAll()
            )
            .logout(
                    logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
