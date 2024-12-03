# 🏥 Hospital Hiberus 🏥
*Proyecto de Desarrollo de Software en la **Hiberus University**, con microservicios.*

## Guía de uso 📋
Primero clonar el repositorio en nuesta máquina
<br>
```git clone https://github.com/rubendesrod/HospitalHiberus.git```
<br>

Vamos a la carpeta raíz del proyecto [HospitalHiberus](./) y ejecutamos ````mvn clean```` ````mvn install````
una vez se hayan generado las carpetas ***/target***, arrancamos sonar con ````docker-compose up sonarqube```` [si se quiere ejecutar en segundo plano añade **-d** ].

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
y creamos nuestro Token, el cuál copiaremos e introduciremos en el [pom.xml](./pom.xml) del proyecto princial o en el de los microservicios si se quiere subir cada uno de ellos por separado, cambiando la properties de sonar.token
por el token que hemos generado

lo siguientes es hacer que nuestro proyecto se suba a sonar, vamos a la carpeta principal de cada uno de los microservicio 
o solo en la prinicipal, y ejecutamos ````mvn sona:sonar````, ahora se podría ver como en el apartado de proyects en sonar nos aparece nuestros microservicios o el proyecto entero

Cuando ya hemos subido los proyectos java a sonar, ahora toca subir el de typescript [microservicio-medicos](./medicos) abrimos una consola en  la capeta raíz,
 ejecutamos ```npm install``` y ejecutamos ```npx jest --coverage``` para generar un reporte LCOV para sonar
y por ultimo ejecutamos ```npx sonar-scanner```, y nuestro proyecto empezará a subirse a sonar

Ir a la carpeta de ``cd /pago-factura`` y ejecutar los siguientes comando

```bash 
  # Para que funcione el producer de python
  pip install confluent-kafka[avro]
  pip install urllib3
 ```
[ *El script de pyhton que contiene esa carpeta nos efectua el pago de las facturas de un medico, uso ```python pruducer_factura.py <IdFactura>```* ]

Ahora nuestro proyecto tiene dos maneras de ser arrancado, aunque si o si kafka, zookeeper, schema-registry, las bases de 
datos, sonar, kowl y zipkin tienen que ser arrancado con contenedores, los microservicios
pueden ser arrancados desde sus ApplicationMain.

1. config-server
2. eureka-server
3. gateway-server
4. Cualquier servicio

*Si el microservicio medicos se quiere arrancar en local habría que ir desde la terminal a la carpeta raiz del microservicio 
[medicos](./medicos/), y ejecutar lo siguiente ```npm install``` y lo siguiente sería ```npm start``` o ```npm run start```*

Si queremos ejecutar todo con docker, podemos hacer un ```docker-compose up -d``` y se ejecutarían todos los contenedores en
segundo plano.

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