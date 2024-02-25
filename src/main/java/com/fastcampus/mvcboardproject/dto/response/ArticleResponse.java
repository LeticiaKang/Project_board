package com.fastcampus.mvcboardproject.dto.response;

import com.fastcampus.mvcboardproject.dto.ArticleDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {

    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }

    // 맵퍼 : 게시글 목록을 보여줄 때, 기본적으로 닉네임을 보여주고 없으면 아이디를 보여준다.
    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}
