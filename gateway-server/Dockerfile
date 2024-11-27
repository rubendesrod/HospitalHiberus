FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 9000

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]