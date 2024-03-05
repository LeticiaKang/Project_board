package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.domain.constant.SearchType;
import com.fastcampus.mvcboardproject.dto.ArticleDto;
import com.fastcampus.mvcboardproject.dto.ArticleWithCommentsDto;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/*
    엔티티를 노출시키지 않아야 한다.
 */

@Slf4j
@RequiredArgsConstructor // 필수 필드에 대한 생성자를 자동 생성
@Transactional
@Service                // 서비스 빈으로 등록
public class ArticleService {

    private final UserAccountRepository userAccountRepository;
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
            case HASHTAG -> articleRepository.findByHashtagNames(Arrays.stream(searchKeyword.split(" ")).toList(), pageable)
                                                                            .map(ArticleDto::from);
        };
    }

    // 게시글 아이디를 통해 조회하는 메서드
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        try {
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleRepository.save(dto.toEntity(userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("게시글 저장 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());   //articleRequest.toDto(boardPrincipal.toDto()

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.title() != null) { article.setTitle(dto.title()); }   //null 방어로직
                if (dto.content() != null) { article.setContent(dto.content()); }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticle(long articleId, String userId) {
        try{
            articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
        }catch (EntityNotFoundException e){
        }
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    // 해시태그 검색(해시태그 있고 검색어가 없으면 비어 있는 페이지를 반환해야 함)
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {

        if (hashtag == null || hashtag.isEmpty() || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtagNames(null,
                pageable).map(ArticleDto::from);
    }

    // 유니크한 해시태그를 불러옴
    @Transactional(readOnly = true)
    public List<String> getHashtags() {
        List<String> uniqueHashtags = articleRepository.findAllDistinctHashtags();

        return uniqueHashtags;
    }

}
