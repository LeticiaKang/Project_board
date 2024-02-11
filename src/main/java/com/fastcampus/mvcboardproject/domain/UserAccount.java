package com.fastcampus.mvcboardproject.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor //기본 생성자 자동 생성
@Entity
public class UserAccount extends AuditingFields {
    @Id
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

    protected UserAccount() {}

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
