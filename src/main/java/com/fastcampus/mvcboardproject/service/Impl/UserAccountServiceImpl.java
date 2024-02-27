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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public ResponseEntity saveUser (UserAccountDto userDto) {

        log.error("[User 서비스] userDto : {}", userDto);

        Optional<UserAccount> existingUser = userAccountRepository.findById(userDto.toEntity().getUserId());

        log.error("[User 서비스] existingUser : {}", existingUser);

        // 아이디 중복 검사
        if (existingUser.isEmpty()) {
            UserAccount user = userDto.toEntity();

            log.error("[User 서비스] user엔티티 : {}", user);

            userAccountRepository.saveAndFlush(user);

            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            return new ResponseEntity("fail", HttpStatus.OK);
        }
    }
}
