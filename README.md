# Project_board
간단한 게시판 구현  
배포 : [Project_board 사이트](http://43.202.19.119:18082/) (클릭!)
  
## 프로젝트 목적
- Spring Boot를 이용한 웹 개발 기술을 습득하고, 스스로 공부하고 문제를 해결해 나가기 위함.  
- MVC패턴을 익히고 AWS를 이용한 배포까지 완료하여 개발의 전체 과정을 경험하고, git으로 형상관리를 한다.  
- 수동적 학습이 아닌 능동적 학습을 한다.  
   
## 어려웠던 점
- 에러가 발생할 때, 주변에 도와주는 사람이 없다보니 해결을 위해 2 ~ 3일씩 고민하는 시간이 많았습니다. 고민의 끝은 항상 문제를 해결하는 것이었지만, 문제의 원인과 해결 방법을 찾는 과정에서 시간이 많이 소요되어 조금 더 빠르게 해결하고 싶은 마음과 상충되는 점이 아쉽고 힘든 부분이었습니다.   
- 하지만, 에러를 해결과정에 많이 성장한다는 것을 느꼈습니다. 이전에는 읽지 못 했던 코드를 읽고, 동작 원리를 이해하면서 웹 동작에 대해 깊이 이해할 수 있게 되었습니다.  
- 회원가입과 로그인을 구현하는 부분이 어려웠지만, 작은 프로젝트를 만들어 연습 하고, 해당 내용을 게시판 프로젝트에 적용하면서 문제를 해결해 나갔습니다.

## 문제 해결 과정
### 1. 로그인이 안되는 이슈
   - 문제 : Spring Security를 이용하여 비밀번호 암호화를 하여 DB에 저장했음에도 로그인이 안되는 상황
   - 해결 방법
      1) Security Document, 구글링, youtube 참고 : Authentication 원리를 정리하고 이해하기 위해 노력, Stackoverflow등 이용
      2) 디버깅 및 로그 분석 : 라인마다 log를 남기도록 설정하고, 객체를 확인. 또한 디버깅 모드로 오류 발생 위치 확인.
      3) 원인 파악 및 해결 : 사용자 인증 및 권한에 대한 필터 체인을 재설정하고(post, login추가), Request로 데이터를 잘 
                                   받아오도록 순서와 name 매칭을 확인하여 수정
      4) 테스트 : 모의 테스트 객체를 파일 안에 만들어 회원가입이 되는지 테스트 케이스를 만들어 확인

### 2. QueryDSL 의존성을 설정했음에도, persistence/NoResultException 에러 발생
   - 해결 방법
      1) Stackoverflow를 이용하여 동일한 에러에 대한 해결방법 조사 및 적용
      2) QueryDSL의존성을 다시 확인, 버전 함께 명시하여 적용해보기도 함.
      3) 디버깅으로 에러 위치 찾기, 에러 로그 분석하기
      4) SpringBoot3에서 javax에서 jakarta로 변경되어, 모두 의존성을 jakarta로 적용했지만 querydsl-jpa는 따로 버전과
          jakarta를 명시해줘야 했고, qureydsl의 공식 Githun 이슈를 보다가 해결방법을 찾게 되어 해결하였다.

## 요구사항(주요 기능)
- **회원가입 및 로그인**
- **접근 제한** : 메인 페이지(누구나), 그 외(로그인한 상태)
- **게시글, 댓글 생성/조회/수정/삭제**
- **검색** : 제목, 본문, 작성자, 닉네임, 해시태그 기반의 검색이 가능하다.
- **해시태그 페이지** : 모든 해시태그를 보여주며, 해시태그를 누르면 관련 게시글 목록을 보여준다.
- **게시글 정렬** : 제목, 작성자, 해시태그, 날짜순으로 정렬이 가능하다.

## 개발 환경
* Intellij IDEA Ultimate 2023.2.6
* Java 17
* Gradle 8.2
* Spring Boot 3.2.2

## 기술 세부 스택
- Spring Boot
  * Spring Boot Actuator / Spring Web / Spring Data JPA / Spring Security / Spring Boot DevTools / Spring Configuration Processor
  * Rest Repositories / Rest Repositories HAL Explorer / H2 Database / MySQL Driver / QueryDSL 5.0.0
  * Thymeleaf / Lombok
- 배포
  * AWS EC2
  * AWS RDS(MYSQL)

## git branch 전략
- **main, develop, feature 브런치** 사용
  - **main** : 주요 브런치
  - **develop** : 개발 브런치
  - **feature** : 기능별 개발 브런치
      
- develop에서 기능별로 feature를 생성 (예: #44-search)
  - 기능 구현이 완료되면, develop branch에 merge
  - 큰 단위별로 develop branch에서 main branch로 merge

## 데모 영상
<img width="80%" src="https://github.com/LeticiaKang/Project_board/assets/87592790/9baf3f32-24f5-4ff0-a0c8-515e1661c261)https://github.com/LeticiaKang/Project_board/assets/87592790/9baf3f32-24f5-4ff0-a0c8-515e1661c261">
