# open jdk java17
FROM openjdk:17-jdk

# JAR_FILE 변수 정의(2개의 JAR파일 중 하나)
ARG JAR_FILE=./build/libs/mvc-board-project-0.0.1-SNAPSHOT.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${JAR_FILE} app.jar

# 시스템 진입점 정의
ENTRYPOINT ["java", "-jar", "/app.jar"]