package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.type.SearchType;
import com.fastcampus.mvcboardproject.dto.ArticleDto;
import com.fastcampus.mvcboardproject.dto.ArticleUpdateDto;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

// 스프링 부트 슬라이스 테스트를 사용하지 않음(대신 mockito 사용) - 스프링부트 애플리케이션이 띄우는 시간을 생략함
@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;  // sut : subject under test 테스트 대상을 의미 / @InjectMocks : 목을 주입하는 대상
    @Mock private ArticleRepository articleRepository;

    @DisplayName("[게시글 검색] 게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // Given

        // When
        // 제목 본문 아이디 닉네임 해시태그 검색
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

        // Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("[게시글 조회] 게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given

        // When
        ArticleDto articles = sut.searchArticle(1L);

        // Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("[새 게시글 저장] 게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        // Given
        // articleRepository는 Mock 객체이며, save() 메서드 호출에 어떤 종류의 Article 객체가 전달되더라도(null 값이라도) null을 반환
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        // 저장될 article 생성
        ArticleDto articleDto = ArticleDto.of(LocalDateTime.now(), "Uno", "title", "content", "#java");
        // ArticleService에서 저장 메서드로 게시글 저장
        sut.saveArticle(articleDto);

        // Then
        // articleRepository 객체에 대해 save() 메서드가 호출되었는지를 검증
        // any(Article.class) : 어떤 종류의 Article 객체가 전달되었는지에 관계없이 호출 / 호출 안했으면 실패
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("[게시글 수정] 게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        // Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        // ArticleService에서 수정 메서드로 변경
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#java"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("[게시글 삭제] 게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
        // Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }

}