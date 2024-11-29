FROM openjdk:17-jdk-slim
ADD target/*.jar app.jar
EXPOSE 8761

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]