# webshop-basics

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
