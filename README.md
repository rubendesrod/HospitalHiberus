# 🏥 Hospital Hiberus 🏥

## Guía de uso 📋
// TODO:

## Documentación 🗒️
* Config server: http://localhost:8888/<nombre_servicio>/default
* Eureka server: http://localhost:8761
* Gateway server: http://localhost:9000/<servicio>
* Kowl (Interfaz para Kafka): http://localhost:8080/topics
* Zipkin (Monitorización): 

#### URLs al swagger
* Pacientes: http://localhost:8086/swagger-ui/index.html
* Medicos: http://localhost:8082/docs
* Citas: http://localhost:8083/swagger-ui/index.html
* Consultador Histotrial Médico: http://localhost:8084/swagger-ui/index.html
* Consultador de Facturas: http://localhost:8085/swagger-ui/index.html

## Diagrama 🎛️
![Diagrama.jpg](imagenes%2FDiagrama.jpg)

## Pruebas
* todos los test contienen test Unitarios con Mockito
* las pruebas de conexión se realizan con postmas [Fichero->Hospital_Hiberus.postman_collection.json]

## Descripción
##### API realizada como trabajo en la Hiberus University, cuenta con diferentes tecnologías y lenguajes, encargada de realizar:
1. CRUD de clientes
2. CRUD de médicos
3. CRUD de citas + comunicacion con otros servicio + producer a topics
4. consultador-historialMedico atiende peticiones get y obtiene datos de la DB
5. consumer-historiaMedico escucha la cola de mensajes en un topic
6. consumer-facturas escucha de 2 topics, uno para la creación de la factura y otro para la confirmacion del pago
7. consultador-facturas atiende peticiones get y obtiene mensaje de la DB

## Tecnologias
### comunicación y despliegue
* Spring cloud [Config Server, Gateway, Eureka]
* Kafka
* Zipkin
* Docker
* github

### Sistemas gestores de Bases de datos
* MongoDB
* PostgreSQL
* MySQL

### Lenguajes
* Java [Spring Boot]
* TypeScript [Node.Express]


## Autor
### © Rubén Descalzo Rodríguez