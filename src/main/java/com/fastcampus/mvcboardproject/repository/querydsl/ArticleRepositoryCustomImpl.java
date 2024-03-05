package com.fastcampus.mvcboardproject.repository.querydsl;

import com.fastcampus.mvcboardproject.domain.Article;
import com.fastcampus.mvcboardproject.domain.QArticle;
import com.fastcampus.mvcboardproject.domain.QHashtag;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collection;
import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtags.any().hashtagName)
                .fetch();
    }

    @Override
    public Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable) {
        // Article 및 Hashtag 엔티티에 대한 querydsl 경로 생성
        QHashtag hashtag = QHashtag.hashtag;
        QArticle article = QArticle.article;

        // 해시태그 이름을 기반으로 기사를 가져오는 JPQL 쿼리 작성
        JPQLQuery<Article> query = from(article)
                .innerJoin(article.hashtags, hashtag)
                .where(hashtag.hashtagName.in(hashtagNames));               //해시태그 이름을 기반으로 기사를 필터링
        // 페이지 정보를 사용하여 쿼리에 페이징 적용
        List<Article> articles = getQuerydsl().applyPagination(pageable, query).fetch();

        // 페이징 세부 정보와 함께 가져온 기사를 포함하는 PageImpl 객체 생성
        return new PageImpl<>(articles, pageable, query.fetchCount());
    }

}
