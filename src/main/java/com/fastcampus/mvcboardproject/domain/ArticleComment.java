package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true) // 부모 클래스의 필드도 포함하여 문자열로 출력
@Table(indexes = { //검색을 위한 index를 설정
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false) // 게시글은 여러 댓글을 가질 수 있음. 댓글이 지워진다고 게시글이 지워지면 안됨(cascade설정 안함)
    private Article article; // 게시글 (ID)

    @ManyToOne(optional = false) // 1명의 사용자는 여러 댓글을 가짐. 사용자가 탈퇴되도 글은 남아 있음.
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false, length = 500)
    private String content; // 본문 (null 안됨)

    // Hibernate구현체를 사용하는 경우 기본 생성자를 가지고 있어야 한다. 평소에는 오픈하지 않으거라 protected로 설정, private는 작동 안됨.
    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content, UserAccount userAccount ) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content, UserAccount userAccount) {
        return new ArticleComment(article, content, userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
