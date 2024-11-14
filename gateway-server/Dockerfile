# Imagen de Java
FROM openjdk:17-jdk-slim

# Añado el jar de la aplicación al contenedor
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que el servicio corre
EXPOSE 9000

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]