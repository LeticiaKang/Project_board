package com.fastcampus.mvcboardproject.service.Impl;


import com.fastcampus.mvcboardproject.domain.UserAccount;
import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import com.fastcampus.mvcboardproject.repository.UserAccountRepository;
import com.fastcampus.mvcboardproject.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public void saveUser (UserAccountDto user) {

        log.error("[User 서비스] user : {}", user);
        System.out.println("[User 서비스] user : " + user);

        UserAccount saveUser = UserAccount.of(user.userId(), user.userPassword(), user.email(), user.nickname(), user.memo());
        saveUser.setUserPassword(passwordEncoder.encode(user.userPassword()));

        Optional<UserAccount> existingUser = userAccountRepository.findById(user.toEntity().getUserId());
        log.error("[User 서비스] existingUser : {}", existingUser);
        System.out.println("[User 서비스] existingUser : " + existingUser);

        // 아이디 중복 검사
        if (existingUser.isEmpty()) {

            log.error("[User 서비스] user엔티티 : {}", saveUser);
            System.out.println("[User 서비스] user엔티티 : " + saveUser);

            userAccountRepository.saveAndFlush(saveUser);

//            return new ResponseEntity("success", HttpStatus.OK);
        } else {
//            return new ResponseEntity("fail", HttpStatus.OK);
        }
    }
}
