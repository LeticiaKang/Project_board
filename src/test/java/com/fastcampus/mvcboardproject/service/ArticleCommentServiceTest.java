package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.ArticleComment;
import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.dto.ArticleCommentDto;
import com.fastcampus.mvcboardproject.dto.ArticleCommentUpdateDto;
import com.fastcampus.mvcboardproject.dto.ArticleUpdateDto;
import com.fastcampus.mvcboardproject.repository.ArticleCommentRepository;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private UserAccountRepository userAccountRepository;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("[새 댓글 생성] 댓글을 작성하면 댓글을 생성한다")
    @Test
    void givenCommentIfo_whenSavingComment_thenSavesComment(){
        //Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //When
        ArticleCommentDto articleComment = ArticleCommentDto.of(LocalDateTime.now(), "uno", null,null, "댓글");
        sut.saveArticleComment(articleComment);
        //Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("[댓글 수정] 댓글을 수정하여 저장한다.")
    @Test
    void givenArticleCommentAndModifiedInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        // ArticleService에서 수정 메서드로 변경
        sut.updateArticleComment(1L, ArticleCommentUpdateDto.of("Updated content"));

        // Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @Test
    @DisplayName("[댓글 삭제] 댓글이 성공적으로 삭제되면 삭제된다")
    void givenArticleCommentId_whenDeleteArticleComment_thenDeleteArticleComment() {
        // Given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        // When
        sut.deleteArticleComment(1L);

        // Then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments() {
        // Given
        // 게시글 아이디 정의
        Long articleId = 1L;
        // 유저 생성
        UserAccount user = userAccountRepository.save(UserAccount.of("uno", "pw", "email", "nick", "memo"));
        // 게시글 아이디로 게시글을 찾으면, 특정 아이디를 가진 게시글이 반환
        given(articleRepository.findById(articleId)).willReturn(Optional.of(
                Article.of(user,"title", "content", "#java"))
        );

        // When
        // 게시글 아이디로 조회한 댓글 목록 저장
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        // Then
        assertThat(articleComments).isNotNull();                // 빈값이면 안됨
        then(articleRepository).should().findById(articleId);   // articleRepository가 findById(게시글 아이디) 메서드를 호출했는지 검증
    }
}

