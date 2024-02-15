package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.type.SearchType;
import com.fastcampus.mvcboardproject.dto.ArticleDto;
import com.fastcampus.mvcboardproject.dto.ArticleUpdateDto;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // 필수 필드에 대한 생성자를 자동 생성
@Transactional
@Service                // 서비스 빈으로 등록
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true) // 단지 읽어오는 거
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        // page에 정렬기능
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(long articleId, ArticleUpdateDto dto) { // 수정한 게시글 아이디, 업데이트 할 Article
    }

    public void deleteArticle(long articleId) {
    }

}
