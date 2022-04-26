# Operaciones Terrestres API

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.losilegales.oprterrestres.OprTerrestresApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Proposito

Api con el proposito de suministrar los recursos de operaciones terrestres necesarios al sistema general de vuelo de la aerolinea.

## Que se hizo

 * Estructura de la aplicación.
 * Se hicieron configuraciones generales para la comunicion de maestros y servicios externos.
 * Se genera la documentacion base con swagger para suministrar las operaciones disponibles de la api.
 * Configuraciones esenciales para la comunicacion con la plataforma heroku.




