# webshop-basics rest service

## Prerequisites

**1. Java 11** 

**2. Maven**

**3. Docker**

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

## Database ER model
![er-model](https://user-images.githubusercontent.com/18440632/102690374-441db780-4205-11eb-9577-f5b177d1b666.PNG)

### Initial data
The default schema (public) and the corresponding tables are created on application start.
The initial data is loaded into the database, import file is located at: /main/resources/import.sql

```
INSERT INTO public.customer (id, email, first_name, last_name) VALUES (1, 'tomislav.macek4@gmail.com', 'Tomislav', 'Macek');
INSERT INTO public.product (id, code, description, is_available, name, price_hrk) VALUES (1, '123456', 'productDescription', true, 'productB', 50.00);
INSERT INTO public.product (id, code, description, is_available, name, price_hrk) VALUES (2, '12345', 'productDescription', true, 'productA', 10.00);
INSERT INTO public.product (id, code, description, is_available, name, price_hrk) VALUES (3, '1234567', 'productDescription', true, 'productC', 20.00);
INSERT INTO public.webshop_order (id, status, total_price_eur, total_price_hrk, customer_id) VALUES(1, 'DRAFT', 0, 0, 1);
INSERT INTO public.webshop_order_item (id, quantity, order_id, product_id) VALUES(2, 1, 1, 1);
INSERT INTO public.webshop_order_item (id, quantity, order_id, product_id) VALUES(1, 1, 1, 2);
```



## REST docs

After application starts generated documentation is available on:
```bash
  http://localhost:8080/docs/index.html
```
