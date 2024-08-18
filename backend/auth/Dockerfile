# Utiliser une image de base Maven pour la compilation
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /auth/
COPY . .
RUN mvn clean package -DskipTests

# Utiliser une image de base Java pour l'exécution
FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y iputils-ping && rm -rf /var/lib/apt/lists/*
COPY --from=build /auth/target/auth-0.0.1-SNAPSHOT.jar auth-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
