FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/healthbot.jar healthbot.jar

ENTRYPOINT ["java", "-jar", "healthbot.jar"]