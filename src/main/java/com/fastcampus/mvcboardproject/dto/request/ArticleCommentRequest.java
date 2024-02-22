package com.fastcampus.mvcboardproject.dto.request;

import com.fastcampus.mvcboardproject.dto.ArticleCommentDto;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}
