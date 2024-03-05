package com.fastcampus.mvcboardproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)                                     // auditing필드까지 출력되도록 callSuper, 쉽게 출력되도록 ToString
@Table(indexes = {
        @Index(columnList = "hashtagName", unique = true),      // 자주 접근하는 해시태그 이름을 인덱스로 설정
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity  // 엔티티임을 명시
public class Hashtag extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude                                           // 순환참조를 막기 위해 toString
    @ManyToMany(mappedBy = "hashtags")
    private Set<Article> articles = new LinkedHashSet<>();      // 중복이 없어야 하므로, set을 사용함.

    @Setter
    @Column(nullable = false)
    private String hashtagName;                                 // 해시태그 이름

    protected Hashtag() {}                                      // 기본 constructor

    private Hashtag(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    public static Hashtag of(String hashtagName) {
        return new Hashtag(hashtagName);
    }       // 생성자로 인스턴스 생성


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hashtag that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
