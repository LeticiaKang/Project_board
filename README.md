# Project_board
간단한 게시판 구현
## 개발 환경
* Intellij IDEA Ultimate 2023.2.6
* Java 17
* Gradle 7.4.1
* Spring Boot 3.2.2
## 기술 세부 스택
- Spring Boot
* Spring Boot Actuator
* Spring Web
* Spring Data JPA
* Rest Repositories
* Rest Repositories HAL Explorer
* Thymeleaf
* Spring Security
* H2 Database
* MySQL Driver
* Lombok
* Spring Boot DevTools
* Spring Configuration Processor

그 외
* QueryDSL 5.0.0
* Heroku
## git branch 전략
- git hub 전략 사용 (main, develop, feature) 만 사용
- develop에서 기능별로 feature를 생성 (예: #44-search)
- 기능 구현이 완료되면, develop branch에 merge
- 큰 단위별로 develop branch에서 main branch로 merge
## 요구사항
- 게시글은 제목, 작성자, 해시태그, 날짜 순으로 정렬할 수 있다.
- 해시태그별로 게시글 조회가 가능한 페이지가 있으며, 해당 해시태그를 가지고 있는 게시글 목록을 보여준다.
- 게시글, 댓글 생성/조회/수정/삭제가 가능하다.(단, 로그인한 경우)
- 게시판 내 특정 게시글 검색이 가능하다.
- 게시글, 댓글은 작성자에 의해서만 수정, 삭제가 가능하다.
## 데모 페이지
(준비중)
