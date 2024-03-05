package com.fastcampus.mvcboardproject.dto;

import com.fastcampus.mvcboardproject.domain.Article;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsDto(
        Long id,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){
    // 필드 정보를 입력받아 댓글과 게시글이 같이 있는 DTO(ArticleWithCommentsDto)를 생성
    public static ArticleWithCommentsDto of(Long id,
                                            UserAccountDto userAccountDto,
                                            Set<ArticleCommentDto> articleCommentDtos, //댓글 정보 TODO: 왜 set을 사용했는가?
                                            String title,
                                            String content,
                                            Set<HashtagDto> hashtagDtos,
                                            LocalDateTime createdAt,
                                            String createdBy,
                                            LocalDateTime modifiedAt,
                                            String modifiedBy) {
        return new ArticleWithCommentsDto(id,
                userAccountDto,
                articleCommentDtos,
                title,
                content,
                hashtagDtos,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy);
    }

    // 게시글 엔티티(entity)를 DTO(ArticleWithCommentsDto)로 변환
    public static ArticleWithCommentsDto from(Article entity) {
        return new ArticleWithCommentsDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtags().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
