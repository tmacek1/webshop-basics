# webshop-basics

## How it works:

### Prerequisites
**1. Docker. First you need to install docker**
* Download Docker [Here](https://docs.docker.com/docker-for-windows/install/). Hint: Enable Hyper-V feature on windows and restart;
* Then open powershell and check:
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

## Building and deploying the application

### Building the application

The project uses [Maven](https://maven.apache.org/) as a build tool.

To compile/build the project jar execute the following commands:

```bash
  mvn compile
  mvn package
```

### Running the application

Create the docker image of the application by executing the following command:

```bash
  mvn spring-boot:build-image -f pom.xml
```

Run the distribution by executing the following command:

```bash
  docker-compose up
```

This will start the API container exposing the application's port
(set to `8080` in application.properties).

In order to test if the application is up, you can call its health endpoint:

```bash
  curl http://127.0.0.1:8080/actuator/health
```

You should get a response similar to this:

```
  {"status":"UP"}
```
