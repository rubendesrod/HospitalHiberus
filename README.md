# 🏥 Hospital Hiberus 🏥

## Guía de uso 📋
// TODO
```bash 
  # Para que funcione el producer de python
  pip install confluent-kafka[avro]
  pip install urllib3
 ```

## Documentación 🗒️
* Config server: http://localhost:8888/nombre_servicio/default
* [Eureka server](http://localhost:8761)
* Gateway server: http://localhost:9000/EndPoint
* [Kowl (Interfaz para Kafka)](http://localhost:8080/topics)
* [Zipkin (Monitorización de Trazas)](http://localhost:9411/zipkin/)

#### URLs al swagger ⛓️‍💥
* [Swagger Pacientes](http://localhost:8086/swagger-ui/index.html)
* [Swagger Medicos](http://localhost:8082/docs)
* [Swagger Citas](http://localhost:8083/swagger-ui/index.html)
* [Swagger Historial Medico](http://localhost:8084/swagger-ui/index.html)
* [Swagger Facturas](http://localhost:8085/swagger-ui/index.html)

## Diagrama 🎛️
![Diagrama.jpg](imagenes%2FDiagrama.jpg "Diagrama de la comunicación de los Microservicios")

## Pruebas
* Todos los test contienen test Unitarios con Mockito, Junit5
* Para ejecutar los test de Typescript [tener la base de datos de docker en ejecución]
    ```bash
    # Instalar las dependencias
    npm install 
    # Compilar proyecto Ts a Js
    npm run build
    # Ejecutar los test realizados con Jest
    npm test
    ``` 
* Para ejecutar los test de Pyhton [tener tener Kafka, Zookeper y Schema-registry levantado en docker]
    [Fichero de requerimientos](./pago-factura/requerido.txt)
  ```bash
    # Dependencia para el test
    pip install testcontainers
    #[Para ejecutar el test]
    python -m unittest kafka_producer_consumer_test.py
    ``` 
* Las pruebas E2E se han hecho manualmente con postman
  * [Fichero para importar en Postman](Hospital_Hiberus.postman_collection.json)

## Descripción
##### API realizada como trabajo en la Hiberus University, cuenta con diferentes tecnologías y lenguajes, encargada de realizar:
1. **pacientes** CRUD
2. **medicos** CRUD
3. **citas** CRUD + comunicacion con otros servicio + producer a topics
4. **consultador-historialMedico** atiende peticiones get y obtiene datos de la DB
5. **consumer-historiaMedico** escucha la cola de mensajes del topic de kafka
6. **consumer-facturas** escucha de 2 topics, uno para la creación de la factura y otro para la confirmacion del pago
7. **consultador-facturas** atiende peticiones get y obtiene mensaje de la DB
8. **producer-facturas** productor a un topic d

## Tecnologias
### comunicación y despliegue
* Spring cloud [ Config Server, Gateway, Eureka ]
* Kafka [ Zookeper, Schema-Registry, Kowl ]
* Zipkin [ Monitorización en la trazas de los servicios ]
* Docker [ Docker-compose, Dockerfile ]
* github [ control de versiones ]

### Sistemas gestores de Bases de datos
* MongoDB [ Historial Medico ]
* PostgreSQL [ Citas, Pacientes ]
* MySQL [ Facturas, Medicos ]

### Lenguajes
* Java [Spring Boot]
* TypeScript [Node.Express]
* Pyhton


## Autor
### © Rubén Descalzo Rodríguez