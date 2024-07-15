FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/eureka-server-0.0.1-SNAPSHOT.jar eurekaserver.jar
ENTRYPOINT ["java","-jar","/eurekaserver.jar"]
