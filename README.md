# Comment App


## Introduction
- This project is just a simple implementation of a comment app using  **Spring Boot** and event driven Microservices 

## Getting started


### Required software

The following are the initially required software pieces:

1. **Maven**: Apache Maven is a software project management and comprehension tool, it can be downloaded from here https://maven.apache.org/download.cgi

2. **Java 11**: it can be downloaded and installed from https://jdk.java.net/java-se-ri/11


3. **Docker Desktop**: The fastest way to containerize applications on your desktop, and you can download it from here [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)



#### Compose and run the dockers

Inside "comment-app" folder run
> docker-compose up -d

to compose and run the dockers

#### First: Build & Install 

Inside "comment-app" folder run

> mvn clean install


#### Second: Running microservices

Inside "comment-app" folder run in seperate tabs for each microservice


- **EUREKA - SERVER**
> java -jar eureka-server/target/eureka-server-1.0-SNAPSHOT.jar

- **APIGWs**
>java -jar apigw/target/apigw-1.0-SNAPSHOT.jar

- **COMMENT - SERVICE**
>java -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar

- **DASHBOARD - SERVICE**
>java -jar dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar

- **COMMENT - SERVICE 2**
>java -jar -Dserver.port=8085 -jar comment-service/target/comment-service-1.0-SNAPSHOT.jar

- **DASHBOARD - SERVICE 2s**
>java -jar -Dserver.port=8086 -Drabbitmq.queues.dashboard=comment.queue2  dashboard-service/target/dashboard-service-1.0-SNAPSHOT.jar


#### Access RabbitMQ
In browser point to this URL [http://localhost:15672](http://localhost:15672) `username: guest` and `password: guest`.


#### Access PgAdmin
In browser point to this URL [http://localhost:5050](http://localhost:5050) `username: user` and `password: password`.

#### Eureka Server
In browser point to this URL [http://localhost:8761/](http://localhost:8761/) 

#### Get Index Page
In browser point to this URL [http://localhost:8083/api/v1/dashboard/getIndex](http://localhost:8083/api/v1/dashboard/getIndex)



 <br /> 
 
**_TO DO :_**
- Unit and Integration tests

