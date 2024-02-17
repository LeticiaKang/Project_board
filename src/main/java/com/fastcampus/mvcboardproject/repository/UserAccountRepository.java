package com.fastcampus.mvcboardproject.repository;

import com.fastcampus.mvcboardproject.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
