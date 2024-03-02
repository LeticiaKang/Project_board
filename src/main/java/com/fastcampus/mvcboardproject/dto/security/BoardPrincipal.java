package com.fastcampus.mvcboardproject.dto.security;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,  //계정이 가지고 있는 권한 목록
        String email,
        String nickname,
        String memo
) implements UserDetails {

    /*
        (이름, 비밀번호, 이메일, 닉네임, 메모)에 대한 매개변수를 입력하면
        RoleType에서 USER값을 defaulf 넣어, BoardPrincipal 객체로 반환한다.
        BoardPrincipal 객체는 (이름, 비밀번호, 권한정보, 이메일, 닉네임, 메모)를 가지고 있다.
     */
    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        log.debug("[로그][BoardPrincipal][of] roleTypes : {}", roleTypes);

        BoardPrincipal userHasRole =  new BoardPrincipal(
                                                        username,
                                                        password,
                                                        roleTypes.stream()
                                                                .map(RoleType::getName)   // USER
                                                                .map(SimpleGrantedAuthority::new)     // SimpleGrantedAuthority("USER")
                                                                .collect(Collectors.toUnmodifiableSet())      // 수정이 불가한 SET으로 변환
                                                        ,
                                                        email,
                                                        nickname,
                                                        memo
                                                );
        log.debug("[로그][BoardPrincipal][of] userHasRole : {}", userHasRole);

        return userHasRole;
    }

    public static BoardPrincipal from(UserAccountDto dto) {   //유저 DTO를 넣어주면, 권한 정보를 set타입으로 바꿔서 반환해준다.
        log.debug("[로그][BoardPrincipal][from] dto : {}", dto);

        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        log.debug("[로그][BoardPrincipal][toDto] ");
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }


    @Override public String getUsername() {

        log.debug("[로그][BoardPrincipal][getUsername] username: {}", username);
        return username;
    }

    @Override public String getPassword() {
        log.debug("[로그][BoardPrincipal][getPassword] password : {}", password);
        return password;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        log.debug("[로그][BoardPrincipal][getAuthorities] authorities : {}", authorities);
        return authorities;
    }  //권한에 대한 정보

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }

}
