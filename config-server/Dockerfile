FROM openjdk:17-jdk-slim
ADD target/*.jar app.jar
EXPOSE 8888

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]