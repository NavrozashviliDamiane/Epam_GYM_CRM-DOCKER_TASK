FROM openjdk:21-jdk-slim

WORKDIR /app

COPY /src/main/resources/application.yaml application.yaml

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "-Dspring.config.location=/app/application.yaml", "app.jar"]
