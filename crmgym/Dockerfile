FROM openjdk:21-jdk-slim

WORKDIR /app

COPY /src/main/resources/application-prod.yml application.yaml

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=/app/application.yaml", "app.jar"]
