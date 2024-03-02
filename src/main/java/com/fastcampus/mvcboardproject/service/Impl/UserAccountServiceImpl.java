package com.fastcampus.mvcboardproject.service.Impl;

import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import com.fastcampus.mvcboardproject.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser (UserAccountDto user) {

        log.debug("[로그][UserAccountServiceImpl][saveUser] 회원가입 데이터 저장");
        log.debug("[로그][UserAccountServiceImpl][saveUser] 1. user : {}", user);

        UserAccount saveUser = UserAccount.of(user.userId(), user.userPassword(), user.email(), user.nickname(), user.memo());
        saveUser.setUserPassword(passwordEncoder.encode(user.userPassword()));

        // 아이디 중복 검사
        Optional<UserAccount> existingUser = userAccountRepository.findById(user.toEntity().getUserId());
        log.debug("[로그][UserAccountServiceImpl][saveUser] 2. existingUser : {}", existingUser);

        if (existingUser.isEmpty()) {
            log.debug("[로그][UserAccountServiceImpl][saveUser] 3. saveUser : {}", saveUser);
            userAccountRepository.saveAndFlush(saveUser);

        }
    }
}
