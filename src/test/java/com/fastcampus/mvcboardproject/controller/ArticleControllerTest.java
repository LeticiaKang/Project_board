package com.fastcampus.mvcboardproject.controller;

import com.fastcampus.mvcboardproject.config.SecurityConfig;
import com.fastcampus.mvcboardproject.dto.ArticleWithCommentsDto;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Article 컨트롤러 테스트")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class) //모든 컨트롤러를 읽을 필요 없이 ArticleCommentController만 읽도록 설정
class ArticleControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

//    @Disabled("구현 중")
    @DisplayName("[뷰][GET]게시판 목록(게시판) 조회 페이지")
    @Test
    public void GivenNothing_WhenRequestArticlesView_ThenReturnsArticlesView() throws Exception {
        //Given
        // 검색어 없는 경우,
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());

        //When & Then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())                                 // 200인지 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))      //view니까 당연히 text html
                .andExpect(view().name("articles/index"))   // view 이름 확인 url 확인
                //해당 뷰는 데이터가 있어야 함(게시글 목록). model이 있는지 확인.(내용을 검증하는 것은 아니고, 데이터가 있는지만 검사)
                .andExpect(model().attributeExists("articles"));
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
    }

//    @Disabled("구현 중")
    @DisplayName("[뷰][GET]게시글 단건 조회(N번 게시글) 페이지")
    @Test
    public void GivenNothing_WhenRequestSpecificArticleView_ThenReturnsSpecificArticleView() throws Exception {
        //Given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        // When & Then
        mvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())                                                 // 200인지 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))        // html 확인
                .andExpect(view().name("articles/detail"))                  // view 이름 확인 url 확인
                .andExpect(model().attributeExists("article"))                      // model에 게시글 있는지 확인
                .andExpect(model().attributeExists("articleComments"));             // model에 댓글 있는지 확인(null이여도 가져오기 때문에 함)
        then(articleService).should().getArticle(articleId);
    }

    @Disabled("구현 중")
    @DisplayName("[뷰][GET] 게시글 검색 전용 페이지")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())                                                 // 200인지 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))        // html 확인
                .andExpect(model().attributeExists("articles/search"));              // model에 데이터가 있는지 확인
    }

    @Disabled("구현 중")
    @DisplayName("[뷰][GET] 게시글 해시태그 검색 페이지")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())                                                 // 200인지 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))        // html 확인
                .andExpect(model().attributeExists("articles/search-hashtag"));     // model에 데이터가 있는지 확인
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(1L,
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

}
