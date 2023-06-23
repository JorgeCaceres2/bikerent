# Trio Java boilerplate

### Scope

The goal of this project is to act as a starting point to solve the Java Backend "Bike Rent" Trio challenge. 

Is a spring boot application that:
* exposes two endpoints (*/api/bikes* and */api/bikes/[bikeId]*) 
* connects to a database to retrieve data. 

### Stack used
* JDK 17([Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)) 
* Apache maven 3.8.6
* Spring boot 2.7.4
* MySql 5.7
* [Checkstyle for coding standards](https://checkstyle.sourceforge.io/)

### How to run the app locally

* Default profile (this uses MySql database)
  
```mvn spring-boot:run```

* memorydb profile (this uses H2 database)

```mvn spring-boot:run -Dspring-boot.run.profiles=memorydb```

* You should be able to see data here:
  http://localhost:8080/api/bikes

### How to run tests

```mvn test```

### How to run the app using docker
* Navigate to /<project-root>/docker
* Make sure docker daemon is running

```docker-compose up```

This will use **docker-compose.yml** file to set up and start our services (database and app). 
You can add ```-d``` flag to run the process as a daemon.


*Note: you may see that the application is failing to start, is because the database is not ready yet. It will restart until it can run properly.*

*Note 2: check **.env** file for environment variables.*

* To stop services (if running as daemon):

```docker-compose down```

* To apply changes to sources, we need to instruct docker to build again to generate a new image. 

  
  ```docker-compose up --build```

