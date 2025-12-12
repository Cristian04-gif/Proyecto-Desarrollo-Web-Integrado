# -----------------------------
# ETAPA 1: BUILD (Compilación)
# Utilizamos la etiqueta estable de Maven con OpenJDK 21.
# -----------------------------
FROM maven:3.9-openjdk-21 AS build 

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración y código
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el JAR ejecutable.
RUN mvn clean package -DskipTests

# -----------------------------
# ETAPA 2: RUN (Ejecución)
# Utilizamos la imagen ligera Eclipse Temurin JRE 21 (estable).
# -----------------------------
FROM eclipse-temurin:21-jre-alpine 

# Define el puerto interno que Spring Boot escuchará
EXPOSE 8080

# Copia el JAR compilado (Nombre: demo-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]