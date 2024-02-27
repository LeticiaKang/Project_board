package com.fastcampus.mvcboardproject.dto.request;

import com.fastcampus.mvcboardproject.dto.ArticleDto;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;

public record UserAccountRequest (
    String userId,
    String email,
    String password,
    String nickname,
    String memo
) {
    public static UserAccountRequest of(String userId,
                                        String email,
                                        String password,
                                        String nickname,
                                        String memo) {
        return new UserAccountRequest(userId, email, password, nickname, memo);
    }

    public UserAccountDto toDto(UserAccountDto userAccountDto) {
        return UserAccountDto.of(
                userId,
                email,
                password,
                nickname,
                memo);
    }

}
