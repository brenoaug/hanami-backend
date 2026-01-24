FROM eclipse-temurin:21-jre
WORKDIR /app
COPY ./target/hanami-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "hanami-0.0.1-SNAPSHOT.jar"]