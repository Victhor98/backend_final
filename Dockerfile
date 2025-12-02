# Usa una imagen oficial con Java 17 y Gradle
FROM gradle:8-jdk17 AS build
# Copia los archivos del proyecto
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Construye el proyecto, omitiendo tests
RUN gradle build -x test --no-daemon

# Crea la imagen final más ligera
FROM openjdk:17-jdk-slim  # <-- Cambiado a Java 17
# Expone el puerto que usa Railway
EXPOSE $PORT
# Copia el JAR construido
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
