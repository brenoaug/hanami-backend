FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build ./target/hanami-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "hanami-0.0.1-SNAPSHOT.jar"]