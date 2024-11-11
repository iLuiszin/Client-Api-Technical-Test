FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/client-api-0.0.1-SNAPSHOT.jar client-api.jar
ENTRYPOINT ["java", "-jar", "client-api.jar"]
