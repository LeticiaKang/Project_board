package com.fastcampus.mvcboardproject.repository;

import com.fastcampus.mvcboardproject.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
