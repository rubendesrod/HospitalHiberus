# 🏥 Hospital Hiberus 🏥
*Proyecto de Desarrollo de Software en la **Hiberus University**, con microservicios.*

## Guía de uso 📋
Primero clonar el repositorio en nuesta máquina
<br>
```git clone https://github.com/rubendesrod/HospitalHiberus.git```
<br>

lo siguiente es ejecutar el docker-compose ```docker-compose up -d``` [-d por si queremos ejecuar en segundo plano los contenedores, quitar si se quieren ver los log en la terminal]

* En el caso de que el contenedor de sonqube en docker no se inicie con los siguientes pasos se arregla
 ```bash
 # Abrir una teminal en windows
 wsl -d docker-desktop
 # Ejecutar el siguiente comando
 sysctl -w vm.max_map_count=262144
 ```
Hechos ese cambios debería de funcionar el contenedor de sonqube.
 Accedemos al [Sonarqube - click](http://localhost:9090) y metemos los datos admin y admin.
Una vez accedemos a sonar vamos a -> [**My Account -> Security**](http://localhost:9090/account/security)
y creamos nuestro Token, el cuál copiaremos e introduciremos en el [pom.xml](./pom.xml) del proyecto princial, o en el de los microservicios, cambiando la properties de sonar.token
por el token que hemos generado


Una vez se inicien todos los contenedores sin errores, ir a la carpeta de ``cd /pago-factura`` y ejecutar los siguientes comando

```bash 
  # Para que funcione el producer de python
  pip install confluent-kafka[avro]
  pip install urllib3
 ```

Por último, abrir una terminal o nuestro IDE, y ejecutar un (asegurar que esta instalado lombok, o que el IDE tiene activado en las settings del compiler que compile las notaciones)
```mvn clean```  -> ```mvn install``` -> ```mvn sonar:sonar```[En cada microservicio java o en el princial] -> ```mvn surefire:test```, una vez hecho esto para los microservicios de java, ir a la raiz del microservici [medicos](./medicos/), ejecutames el siguiente comando que genera un reporte LCOV para sonar ```npx jest --coverage``` y luego ejecuta ```npx sonar-scanner```

Ya tenemos nuestro proyecto compilado, solo falta arrancar los main de lo microservicios java,
1. config-server
2. eureka-server
3. gateway-server
4. Cualquier servicio


## Documentación 🗒️
* Config server: http://localhost:8888/nombre_servicio/default
* [Eureka server](http://localhost:8761)
* Gateway server: http://localhost:9000/(Endpoint de la API)
* [Kowl (Interfaz para Kafka)](http://localhost:8080/topics)
* [Zipkin (Monitorización de Trazas)](http://localhost:9411/zipkin/)
* [Sonarqube](http://localhost:9090/)

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
* Feign [ Cliente para consultar los microservicios ]
* Zipkin [ Monitorización en la trazas de los servicios ]
* Docker [ Docker-compose, Dockerfile ]
* github [ control de versiones ]

### Sistemas gestores de Bases de datos
* MongoDB [ Historial Medico ]
* PostgreSQL [ Citas, Pacientes ]
* MySQL [ Facturas, Medicos ]

### Lenguajes
* Java [Spring Boot, Mockito, Junit5]
* TypeScript [Node.Express, Jest]
* Pyhton


## Autor
### © Rubén Descalzo Rodríguez