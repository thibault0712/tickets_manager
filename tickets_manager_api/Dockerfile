FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine AS production
COPY --from=build /app/target/tickets_manager_api-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","/tickets_manager_api-0.0.1-SNAPSHOT.jar"]
