package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@ToString
@Table(indexes = { //검색을 위한 index를 설정
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
@AllArgsConstructor //기본 생성자 자동 생성
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false)
    private Article article; // 게시글 (ID)

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false, length = 500)
    private String content; // 본문 (null 안됨)

    // Hibernate구현체를 사용하는 경우 기본 생성자를 가지고 있어야 한다. 평소에는 오픈하지 않으거라 protected로 설정, private는 작동 안됨.
    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

}
