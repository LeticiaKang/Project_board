package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Getter
@ToString
@Table(indexes = { //검색을 위한 index를 설정
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false) // 하나의 게시물은 여러 사용자에 의해 생성됨
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false)
    private String title; // 제목 (null 안됨)

    @Column(nullable = false, length = 10000)
    private String content; // 본문 (null 안됨)

    private String hashtag; // 해시태그

    // Hibernate구현체를 사용하는 경우 기본 생성자를 가지고 있어야 한다. 평소에는 오픈하지 않으거라 protected로 설정, private는 작동 안됨.
    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 동등성, 동일성 검사
    @Override
    public boolean equals(Object o) {
        // article인지 확인하기
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        // id가 null이 아니고(아이디가 부여되지않음), PK인 id가 동일하다면, 같음.
        // (영속화하지 않았을 경우, 동등성 검사에서 탈락)
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
