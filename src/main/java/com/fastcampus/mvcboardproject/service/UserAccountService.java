package com.fastcampus.mvcboardproject.service;

import com.fastcampus.mvcboardproject.dto.UserAccountDto;
import org.springframework.http.ResponseEntity;
public interface  UserAccountService {

    ResponseEntity saveUser(UserAccountDto dto) ;

}
