# Tickets API — Spring Boot + JWT + Docker + H2

API REST para gestión de usuarios y tickets, con autenticación JWT, persistencia en H2 y Dockerización completa.

## Requisitos

- Java 24+
- Maven 3.9+
- Docker (opcional)
- IntelliJ IDEA (recomendado)

## Cómo ejecutar el proyecto localmente

mvn spring-boot:run

La app queda disponible en:
- API → http://localhost:8080
- H2 Console → http://localhost:8080/h2-console  
  JDBC URL: jdbc:h2:file:/data/testdb

## Cómo ejecutar con Docker

### 1. Construir imagen

docker build -t tickets-app .

### 2. Levantar servicios (App + H2)

docker compose up --build

La API queda en:
http://localhost:8080

## Acceso a H2 Console

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:file:/data/testdb;AUTO_SERVER=TRUE
- User: admin
- Password: admin

## Flujo de Autenticación (JWT)

### 1) Registrar un usuario autorizado

curl -X POST http://localhost:8080/api/auth/register 
-H "Content-Type: application/json" 
-d '{
"username": "admin",
"password": "12345"
}'

### 2) Login → devuelve JWT

curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{
"username": "admin",
"password": "12345"
}'

El token viene en el header Authorization:

Ejemplo:
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

## Endpoints de Usuarios

### Crear usuario

curl -X POST http://localhost:8080/users \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{
"name": "Julian",
"lastname": "Casas"
}'

### Obtener usuarios

curl -X GET http://localhost:8080/users \
-H "Authorization: Bearer <TOKEN>"

### Actualizar usuario por ID

curl -X PUT http://localhost:8080/users/e9b1a67d-4f21-4c9b-9a22-12ab34cd5001 \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{
"name": "NuevoNombre",
"lastname": "NuevoApellido"
}'

## Endpoints de Tickets

### Crear ticket

curl -X POST http://localhost:8080/tickets \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{
"description": "No funciona el login",
"userId": "UUID-del-usuario",
"status": "OPEN"
}'

### Obtener todos los tickets

curl -X GET http://localhost:8080/tickets \
-H "Authorization: Bearer <TOKEN>"

### Filtrar por user_id o status

curl -X GET "http://localhost:8080/tickets?userId=1&status=OPEN" \
-H "Authorization: Bearer <TOKEN>"

### Actualizar ticket

curl -X PUT http://localhost:8080/tickets/e9b1a67d-4f21-4c9b-9a22-12ab34cd5001 \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{
"description": "Actualizado",
"userId": "UUID",
"status": "CLOSED"
}'

## Flujo completo

1. Registrar usuario admin (/api/auth/register)
2. Login → obtener JWT (/api/auth/login)
3. Crear usuarios (/users)
4. Crear tickets (/tickets)
5. Consultar y actualizar tickets

## Pruebas

Ejecutar todas las pruebas:

mvn test

Incluye:
- Unitarias (Mockito)
- Integración (MockMvc + JWT real)

## DOCUMENTACIÓN OPENAPI / SWAGGER

Este proyecto incluye documentación interactiva generada automáticamente mediante OpenAPI utilizando springdoc-openapi.

Esto permite:

Visualizar todos los endpoints disponibles

Consultar esquemas, DTOs y códigos de respuesta

### URL DE SWAGGER

Swagger UI
http://localhost:8080/swagger-ui/index.html

## Stack

- Java 24
- Spring Boot 3.x
- Spring Security + JWT
- H2 Database
- Docker
- JUnit y Mockito

## Autor

Julian Casas — Backend Developer
