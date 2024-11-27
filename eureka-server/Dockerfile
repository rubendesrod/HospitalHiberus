FROM openjdk:17-jdk-slim
COPY target/eureka-server-1.0-SNAPSHOT.jar.original app.jar
EXPOSE 8761

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]