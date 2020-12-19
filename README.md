# webshop-basics rest service

## Prerequisites

**1. Java 11** 

**2. Maven**

* Download Maven [Here](https://maven.apache.org/download.cgi)

**3. Docker**  

* Download Docker [Here](https://docs.docker.com/docker-for-windows/install/). Hint: Enable Hyper-V feature on windows and restart;
* Then open terminal and check:
```bash
docker info
docker-compose -v
```

## Building the application

Clone the repository:
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
  curl http://localhost:8080/actuator/health
```

You should get a response similar to this:

```
  {"status":"UP"}
```

## REST docs

After application starts generated documentation should be available on:
```bash
  http://localhost:8080/docs/index.html
```
