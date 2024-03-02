package com.fastcampus.mvcboardproject.dto.request;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;

public record UserAccountRequest (
    String userId,
    String userPassword,
    String email,
    String re_pass,  //미사용
    String nickname,
    String memo
) {

    public UserAccountDto toDto(UserAccountRequest userAccountRequest) {
        return UserAccountDto.of(
                userAccountRequest.userId(),
                userAccountRequest.userPassword(),
                userAccountRequest.email(),
                userAccountRequest.nickname(),
                userAccountRequest.memo());
    }

}
