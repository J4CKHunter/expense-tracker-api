# Expense Tracker API

A simple REST API created using Spring Boot.

### Dependencies:
```
spring-boot-starter-data-jpa
spring-boot-starter-oauth2-resource-server
spring-boot-starter-validation
spring-boot-starter-web
spring-boot-configuration-processor
lombok
spring-boot-starter-test
h2
springdoc-openapi-starter-webmvc-ui
jackson-databind
```


### Creating Asymmetric Key Pair for Signing and Verifying JWT

```
publicKey -> verify JWT, privateKey -> sign JWT

resources -> certs package ->  private.pem ve public.pem  : 

openssl -> user environment variables -> add to C:\Program Files\Git\usr\bin\ 

commant prompt inc certs directory:

- openssl genrsa -out keypair.pem 2048

- openssl rsa -in keypair.pem -pubout -out public.pem

- openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

- delete keypair.pem

```

<br>
<br>

### Docker Commands

```
1. Go to the project location and run the following commands as follows

    docker build -t expense-tracker-app:latest .
     
    docker run -d -p 8080:8080 --name expense-tracker expense-tracker-app:latest

2. you can push image to Dockerhub

3. you can build a pipline(i.e. jenkins) and you can push your app to dockerHub everytime you trigger the pipeline (via git push),
or you can deploy your docker-image to Kubernetes Cluster

! you can either use postman or swagger to test API
    - go to swagger api and discover the API
        http://localhost:8080/swagger-ui/index.html#/

```




