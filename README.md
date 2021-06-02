# cogame

[![Build Status](https://github.com/Klassen-Olga/cogame/workflows/build/badge.svg)](https://github.com/Klassen-Olga/cogame/actions)
[![versionjava](https://img.shields.io/badge/jdk-11,_15-brightgreen.svg?logo=java)](https://github.com/spring-projects/spring-boot)

### Required software
- java >= v11
- Apache Maven >= v3.6.3
- docker >= v20.10.6
- docker-compose >= v1.29.1

### Installation
- Start Docker Desktop
- Install app and create images
```sh
# Install app
git clone https://github.com/Klassen-Olga/cogame.git
# Go to the folder
cd cogame
# Create target
mvn clean install
# Go to the individual modules and create images
cd event-service
mvn spring-boot:build-image

cd ../message-service
mvn spring-boot:build-image

cd ../user-service
mvn spring-boot:build-image

# go to the root of the cogame
cd ..
# run docker-compose
docker-compose up
```

When running the app in docker mode, each request must have an authentication password in the header 
- user-service:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;key 123
- event-service:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;key 456
- message-service:&nbsp;&nbsp;key 789



| Access        | URL    |
| ------------- |:-------------:|
| traefik dashboard     | http://localhost:8080 |
| app      | http://localhost:81      |
| example get users | http://localhost:81/users      |

It is also possible to run individual modules in dev mode.
To access the app you should use ports listed below.
Authentication is not needed.

| Applications        | Ports    | 
| ------------- |:-------------:|
| Event Service     | 8000 |
| User Service      | 8001      |
| Message Service | 8002      | 

