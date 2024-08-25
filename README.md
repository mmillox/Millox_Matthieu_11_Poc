# Millox_Matthieu_11_Poc
 Spring Boot + React + Nginx + JMeter + Docker 

## Frontend / Framework : React.js
Port: 3000
Conteneurisé via Docker, peut être exécuté indépendamment pour le développement ou les tests.
Accessible à l'adresse http://localhost:3000 lors de l'exécution.
Backend Auth (Spring Boot)

## Backend Auth / Framework: Spring Boot
Port: 8080
Utilise MongoDB comme base de données pour la gestion des utilisateurs et des sessions d'authentification.
Conteneurisé via Docker, peut être exécuté indépendamment pour le développement ou les tests.
- Login accessible à l'adresse http://localhost:8080/api/auth/signin lors de l'exécution.
- Register accessible à l'adresse http://localhost:8080/api/auth/signup lors de l'exécution.

## Backend Road / Framework: Spring Boot
Port: 8081
Utilise GraphHopper pour la gestion des cartes et des itinéraires.
Conteneurisé via Docker, peut être exécuté indépendamment pour le développement ou les tests.
- Road accessible à l'adresse http://localhost:8081/api/hospitals lors de l'exécution.

## Nginx
Utilisé comme reverse proxy pour acheminer les requêtes vers les services front-end et back-end appropriés.
Configuration personnalisée pour supporter la gestion des certificats SSL et l'équilibrage de charge.

## JMeter
Utilisé pour effectuer des tests de performance sur les API backend.
Les scénarios de test sont inclus pour vérifier la montée en charge et la résilience des services.
GitHub Workflow (CI/CD)

## Pipelines CI/CD configurés via GitHub Actions:
Frontend Workflow: Pour le build et le déploiement du front-end React.
Backend Workflow: Pour le build et le déploiement des services back-end (authentification et gestion des routes).

## Prérequis
- Docker - Docker Compose (on recommande Docker Desktop pour mieux gérer les images et conteneurs), MongoDB installés sur votre machine.
- Node.js et Maven si vous souhaitez exécuter ou développer les services indépendamment en dehors de Docker.
