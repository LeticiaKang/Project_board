package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.type.SearchType;
import com.fastcampus.mvcboardproject.dto.ArticleDto;
import com.fastcampus.mvcboardproject.dto.ArticleWithCommentsDto;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/*
    엔티티를 노출시키지 않아야 한다.
 */

@Slf4j
@RequiredArgsConstructor // 필수 필드에 대한 생성자를 자동 생성
@Transactional
@Service                // 서비스 빈으로 등록
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)  // 단지 읽어오는 거
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) { // page에 정렬기능

        // 검색어가 없는 경우(빈 문자열이거나 스페이스로 이루어진 경우)
        if (searchKeyword == null || searchKeyword.isBlank()) {

            return articleRepository.findAll(pageable).map(ArticleDto::from);
            // ArticleDto.from(article)를 메서드 레퍼런스로 표현하여 가독성을 높임
            // findAll(pageable)는 page를 반환하고, page는 map을 가지고 있는데,page안에 내용물을 형변환 시켜 다시 page로 바꿔준다.
        }

        // enum을 주제로 각 검색어에 따른 쿼리를 만든다. TODO: 필요하다면 나중에 수정하기, TODO: #을 넣을 때랑 아닐때 모두 동작 가능하게 나중에 수정하기
        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag(searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    // 게시글 아이디를 통해 조회하는 메서드
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)          //dto로 변환
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            // not null필드에 대한 방어로직
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            // 해시태그는 null 가능 필드임
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        try{
            articleRepository.deleteById(articleId);
        }catch (EntityNotFoundException e){
            log.warn("존재하지 않는 게시글 입니다. - articleId: {}", articleId);
        }
    }
}
