FROM openjdk:8u111-jdk-alpine

ARG JAR_FILE=target/profile-*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

RUN echo "Asia/Bangkok" > /etc/timezone

ENTRYPOINT ["java","-jar","/app.jar"]