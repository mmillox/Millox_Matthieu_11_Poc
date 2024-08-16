# Utiliser une image de base Maven pour la compilation
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /road/
COPY . .
RUN mvn clean package -DskipTests

# Utiliser une image de base Java pour l'exécution
FROM openjdk:17-jdk-slim
COPY --from=build /road/target/road-0.0.1-SNAPSHOT.jar road-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "road-service.jar"]
