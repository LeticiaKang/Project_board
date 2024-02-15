package com.fastcampus.mvcboardproject.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@Table(indexes = { //검색을 위한 index를 설정
        @Index(columnList = "userId"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @Setter(AccessLevel.NONE) //유저id는 불변, setter안됨
    private String userId;

    @Column(nullable = false) // 비밀번호는 null이면 안됨
    private String userPassword;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String nickname;

    private String memo;

    // Hibernate구현체를 사용하는 경우 기본 생성자를 가지고 있어야 한다. 평소에는 오픈하지 않으거라 protected로 설정, private는 작동 안됨.
    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }

}
