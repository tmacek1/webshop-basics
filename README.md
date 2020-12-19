# webshop-basics rest service

## Prerequisites

**1. Java 11** 

**2. Maven**

**3. Docker**  

* Download Docker [Here](https://docs.docker.com/docker-for-windows/install/). Hint: Enable Hyper-V feature on windows and restart;
* Then open terminal and check:
```bash
docker info
```
or, and you see versions docker & docker compose
```bash
docker -v
```
```bash
docker-compose -v
```

## Building the application

* Clone the repository:
```bash
git clone https://github.com/tmacek1/webshop-basics.git
```

To compile/build the project jar execute the following commands:

```bash
  mvn clean install
```

## Running the application

Create the docker image of the application by executing the following command:

```bash
  mvn spring-boot:build-image -f pom.xml
```

Run the distribution by executing the following command:

```bash
  docker-compose up
```

This will start the API container exposing the application's port
(predefined to `8080` in application.properties).

In order to test if the application is up, you can call its health endpoint:

```bash
  curl http://127.0.0.1:8080/actuator/health
```

You should get a response similar to this:

```
  {"status":"UP"}
```
