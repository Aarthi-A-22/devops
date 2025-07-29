FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/devop-app-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]