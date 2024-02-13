package com.fastcampus.mvcboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan // 프로퍼티 클래스를 찾아서 빈으로 등록해주는 역할
@SpringBootApplication
public class MvcBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcBoardProjectApplication.class, args);
    }

}
