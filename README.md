# RESTful Login and Registration API Service
Spring Boot backend microservice for user registration and login authentication. Easy to setup and deploy with a built in Tomcat container. Connect any frontend framework such as Angular or React to this application.

### Built With
This application was written in Java.
* [Spring Boot](https://https://spring.io/guides/gs/spring-boot/)
* [Hibernate](https://hibernate.org/)
* [MySQL](https://www.mysql.com/)
* [Gradle](https://gradle.org/)

<!-- GETTING STARTED -->
## Getting Started

This service uses Gradle for depedency management and building the application. A MySQL or other RDBMS SQL server is required in order for account information to be persisted.
Verify a SQL server is running along with proper credentials within application.properties before building or starting the application. After running the Spring Boot application, two endpoints are available. One for authentication the user and another registering new users.

### Prerequisites
#### MySQL Server
Download [MySQL Server 8](https://dev.mysql.com/downloads/mysql/)
* Login to your MySQL server and run the following commands
```mysql
CREATE DATABASE login_app;
CREATE USER 'rootuser'@'%' IDENTIFIED BY 'yourpasswordhere';
USE login_app;
GRANT ALL PRIVILEGES ON login_app.* TO 'rootuser'@'%';
```

#### JWT Provider
This application works in conjunction with JSON Web Token microservice for verifying user identity.
Download [JWT Provider](https://github.com/jasontokarski/JwtProvider)
* Configuration details are provided within the repository.
* Update the variable jwt.hostname inside of application.properties with the URL of your JWT Provider
* Start up the JWT Provider before running the Login and Registration service.
