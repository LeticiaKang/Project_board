package com.fastcampus.mvcboardproject.repository;

import com.fastcampus.mvcboardproject.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}