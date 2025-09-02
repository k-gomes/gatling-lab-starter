# Gatling Demo

Ce projet est une application Java utilisant Spring Boot, Maven et Gatling pour les tests de performance.

## Prérequis

- Java 17
- Maven 3.9.9
- Postgres 12
- Un IDE (ex: IntelliJ IDEA)
- Accès à Internet pour télécharger les dépendances

## Installation

1. Clonez le dépôt :

```shell
git clone <url-du-repo> cd gatling-demo
```

2. Installez la BDD : 

Créer une base de données Postgres nommée `gatlinglab` et exécuter le script SQL situé dans `gatling-lab-starter/db/init.sql` pour créer les tables et insérer des données initiales.

3. Compilez les projets :

```shell
mvn clean install gatling-lab-starter/api-demo
mvn clean install gatling-lab-starter/gatling-demo
```

4. Adapter le fichier de configuration `application.properties` dans `gatling-lab-starter/api-demo/src/main/resources` pour configurer la connexion à la BDD.

## Lancement de l'application

```shell
mvn spring-boot:run
```

## Execution d'un scenario Gatling

```shell
mvn gatling:test -Dgatling.simulationClass=com.gatlinglab.<path du scenario>
```