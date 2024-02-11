package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
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

}
