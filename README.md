# API_REST

# Register Users

## Clone the application

 ```bash
    git clone https://github.com/zaieds/API_REST.git
 ```
 
## Change Path DataBase in application.properties

  -open src/main/resources/application.properties
  
  -Change Path :
  
  spring.datasource.url=jdbc:h2:file:C:/Users/a792843/Documents/formation/Spring/register_user/src/main/resources/db_users;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE;

  -link Base de donnée : http://localhost:8085/h2-console/
  
   **JDBC URL: jdbc:h2:file:C:/Users/a792843/Documents/formation/Spring/register_user/src/main/resources/db_users
  
  **User Name: : sa
  
  **Password : password
   

##  run application :

Alternatively, you can run the app without packaging it using -

 ```bash
mvn spring-boot:run 
 ```
 The app will start running at http://localhost:8085/users
 
 ## Explore Rest APIs
 
  ```bash
 GET /users

POST /users

GET /users/{id}

PUT /users/{id}

PATCH /users/{id}

DELETE /users/{id}
 ```
 
## Logiciel et DB utilisés

**intellij

**H2DataBase

**Postman

**Swagger

**insomnia
  
  
## Link Postman 
  https://go.postman.co/workspace/My-Workspace~852f81f8-cbbe-41a9-8fb8-3604bcd57695/api/7c82bd4d-ff16-4a49-a6b4-72a62d3ebfed
  
## Swagger 
Les fichiers openapi.Json et openapi.ymal son disponible dans le racine pour utiliser Swagger

### Link Swagger : http://localhost:8085/swagger-ui/index.html
### Link Swagger : http://localhost:8085/v3/api-docs

## Versions

**Java :** 11
**Maven :** 4.0.0
**SpringBoot :** 2.5.6
**Java :** 5.0
