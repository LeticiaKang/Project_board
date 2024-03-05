package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true) // 부모 클래스의 필드도 포함하여 문자열로 출력
@Table(indexes = { //검색을 위한 index를 설정
        @Index(columnList = "title"),
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false)
    private String title; // 제목 (null 안됨)

    @Column(nullable = false, length = 10000)
    private String content; // 본문 (null 안됨)
                                                                        // < 해시태그 >는 연관관계로 설정할 것임
    @ToString.Exclude                                                   // 순환참조 막음
    @JoinTable(
        name = "article_hashtag",                                       // 해시태그 테이블과 조인해서 테이블을 하나 만드는데, 그 이름을 article_hashtag로 설정하고
        joinColumns = @JoinColumn(name = "articleId"),                  // 여기서 조인하는 컬럼은 articleId로 이름을 설정하고,
        inverseJoinColumns = @JoinColumn(name = "hashtagId")            // 조인하는 테이블에서 가져오는 컬럼은 hashtagId로 이름을 설정한다.
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})     // 해시태그를 지우면 게시글을 지우는 것이 아니라, 게시글이 지워지면 해시태그 속의 게글을 삭제하도록 설정한다.
    private Set<Hashtag> hashtags = new LinkedHashSet<>();              // PERSIST: insert, MERGE: update와 같은 변경이 있을 때, 동기화를 할 것임

    @ToString.Exclude                                                   // 순환참조를 방지하기 위함
    @OrderBy("createdAt DESC")                                          // createdAt순으로 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)         // cascade = CascadeType.ALL : 양방향 바인딩 적용
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // Hibernate구현체를 사용하는 경우 기본 생성자를 가지고 있어야 한다. 평소에는 오픈하지 않으거라 protected로 설정, private는 작동 안됨.
    protected Article() {}

    private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    // 동등성, 동일성 검사
    @Override
    public boolean equals(Object o) {
        // article인지 확인하기
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        // id가 null이 아니고(아이디가 부여되지않음), PK인 id가 동일하다면, 같음.
        // (영속화하지 않았을 경우, 동등성 검사에서 탈락)
        return this.getId() != null && this.getId().equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
