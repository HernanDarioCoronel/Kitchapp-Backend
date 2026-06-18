FROM maven:3.9-eclipse-temurin-21 AS builder
LABEL authors="https://github.com/HernanDarioCoronel"
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve -DincludeScope=compile || true
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]