# 🏥 Hospital Hiberus 🏥

## Guía de uso 📋
// TODO:

## Documentación 🗒️
* Config server: http://localhost:8888/<nombre_servicio>/default
* Eureka server: http://localhost:8761
* Gateway server: http://localhost:9000/<servicio>
* Kowl (Interfaz para Kafka): http://localhost:8087/topics
* Zipkin (Monitorización): 

#### URLs al swagger
* Pacientes: http://localhost:8081/swagger-ui/index.html
* Usuarios: http://localhost:8082/docs
* Citas: ❌ http://localhost:8083/swagger-ui/index.html ❌
* Consultador Histotrial Médico: http://localhost:8084/swagger-ui/index.html
* Consultador de Facturas: http://localhost:8085/swagger-ui/index.html

## Diagrama 🎛️
![Diagrama.png](imagenes%2FDiagrama.png)

## Descripción
##### API realizada como trabajo en la Hiberus University, cuenta con diferentes tecnologías y lenguajes, encargada de realizar:
1. CRUD de clientes
2. CRUD de médicos
3. CRUD de citas + funcionalidades extras
4. Creación de un historial médico y consulta del mismo
5. Creación de las Facturaciones, pago de estas y consulta

## Tecnologias
### comunicación y despliegue
* Spring cloud:
  * Config Server
  * Gateway
  * Eureka
* Kafka
* Docker

### SGBD
* MongoDB
* PostgreSQL
* MySQL

### Lenguajes
* Java [Spring Boot]
* TypeScript [Node.Express]


## Autor
#### © Rubén Descalzo Rodríguez