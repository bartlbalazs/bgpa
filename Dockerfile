FROM openjdk:8-jdk-alpine
MAINTAINER bartlbalazs@gmail.com
ARG JAR_FILE
COPY ./target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080