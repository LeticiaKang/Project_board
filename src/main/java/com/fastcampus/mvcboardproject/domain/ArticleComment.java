package com.fastcampus.mvcboardproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor //기본 생성자 자동 생성
@Entity
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

    protected ArticleComment() {
    }

}
