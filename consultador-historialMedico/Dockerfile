FROM openjdk:17-jdk-slim
ADD target/*.jar app.jar
EXPOSE 8084

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]