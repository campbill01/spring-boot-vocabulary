FROM gradle:7.3.0-jdk8 AS BUILD
COPY . vocabulary
WORKDIR vocabulary
RUN gradle build --no-daemon

FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY --from=BUILD /home/gradle/vocabulary/build/libs/*SNAPSHOT.jar /app.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
