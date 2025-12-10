# -----------------------------
# ETAPA 1: BUILD (Compilación)
# Utilizamos la imagen más reciente de Maven, que ya trae un JDK (asumimos 24 o superior).
# -----------------------------
FROM maven:latest AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración y código
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el JAR ejecutable. Omitimos las pruebas.
RUN mvn clean package -DskipTests

# -----------------------------
# ETAPA 2: RUN (Ejecución)
# Utilizamos una imagen ligera y específica para Java 24 para la ejecución.
# -----------------------------
FROM openjdk:24-jdk-slim

# Define el puerto interno que Spring Boot escuchará
EXPOSE 8080

# Copia el JAR compilado de la etapa anterior.
# Importante: Usamos el nombre 'demo-0.0.1-SNAPSHOT.jar' que proporcionaste.
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar la aplicación (ENTRYPOINT)
ENTRYPOINT ["java", "-jar", "app.jar"]