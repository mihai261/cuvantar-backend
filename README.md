# Cuvântar

  

## Project Overview

Cuvântar is an application which allows users to enhance their vocabulary skills in Romanian. It uses the idea of flash cards and is based on the Spaced Repetition System. It works by adjusting the time between reviews for each individual vocabulary item, so that it provides an optimal learning experience and improves memorization.  When reviewing a vocabulary card, a correct answer will increase the time interval until its next review. Getting a word's meaning wrong makes the word appear more in the review queue.  

  

##  Functional Requirements

 - There will be 3 applications: the web app, the mobile app and the backend server
 - There will be 2 main actors: the web app user and the mobile app user
 - Both users can start new vocabulary lessons, review vocabulary items and search through all their vocabulary items
 - The backend server will include an API with a graphic interface (e.g. Swagger) which allows the introduction of new entities in the database

  

## Technologies

 - Spring Boot 2 for the backend
 - React for the frontend
 - Ionic for the mobile app (using the React code base from above)
 - MySQL database, hosted on AWS RDS
 - AWS EC2 for hosting Spring Boot app
 - AWS Amplify for hosting React app
 - AWS RDS for hosting database
 - Version control using git

## UML diagram
![alt text](https://github.com/mihai261/cuvantar-backend/blob/main/UML_diagram.png)

## How to run it locally

  - Use *docker-compose.yml* to create a docker container with the MySQL database image needed for local development
  - Set the spring active profile to "local" (you can do this using the env variable SPRING_PROFILES_ACTIVE when running the gradle tasks)
  - Either run the migrations manually using the *flywayMigrate* gradle task or let Spring Boot deal with it later
  - Run the gradle task *bootRun* in order to start your application on port 8080 using the profile specified above (this task will also run the *flywayMigrate* task in the background in case you didn't do it before)
  - Go to http://localhost:8080/swagger-ui/index.html in your browser and you should see the swagger UI
  - In order to make any sort of requests in swagger you will first need to generate an authorization token (the green lock icon in the right corner) using the  API credentials specified in the *application-local.yml* properties file
  - In order to make requests to the lessons, reviews and logout endpoints you will need to include the user-specific token given to you by the login endpoint
