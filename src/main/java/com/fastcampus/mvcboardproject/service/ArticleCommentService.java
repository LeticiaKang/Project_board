package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.ArticleComment;
import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.dto.ArticleCommentDto;
import com.fastcampus.mvcboardproject.repository.ArticleCommentRepository;
import com.fastcampus.mvcboardproject.repository.ArticleRepository;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        // 게시글 아이디를 이용하여 댓글 리스트를 가져온다.
        List<ArticleComment> articleComments = articleCommentRepository.findByArticle_Id(articleId);

        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            // 기존 댓글을 articleComment 저장
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            // 변경하고 싶은 내용이 null이 아니면 댓글을 수정한다.
            if (dto.content() != null) { articleComment.setContent(dto.content()); }
        } catch (EntityNotFoundException e) {
            // 댓글이 존재하지 않으면 경고를 한다.
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteArticleComment(long articleCommentId, String userId) {
        // 1. 댓글 정보 가져오기 -> 없으면 경고   2.댓글 없애기
        try{
            articleCommentRepository.deleteByIdAndUserAccount_UserId(articleCommentId, userId);
        }catch (EntityNotFoundException e){
            log.warn("존재하지 않는 댓글입니다. 댓글 번호: {}", articleCommentId);
        }
    }

}
