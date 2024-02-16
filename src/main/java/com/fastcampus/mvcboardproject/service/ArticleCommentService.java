package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.dto.ArticleCommentDto;
import com.fastcampus.mvcboardproject.dto.ArticleCommentUpdateDto;
import com.fastcampus.mvcboardproject.repository.ArticleCommentRepository;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

    public void updateArticleComment(long l, ArticleCommentUpdateDto content) {
    }

    public void deleteArticleComment(long articleCommentId) {
    }
}
