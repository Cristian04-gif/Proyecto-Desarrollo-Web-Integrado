# -----------------------------
# ETAPA 1: BUILD (Compilación)
# Utilizamos la imagen oficial de Maven con la etiqueta '21-jdk' (que es estable).
# -----------------------------
FROM maven:21-jdk AS build 

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración y código
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el JAR ejecutable.
RUN mvn clean package -DskipTests

# -----------------------------
# ETAPA 2: RUN (Ejecución)
# Utilizamos una imagen ligera y estable de Java 21
# -----------------------------
FROM openjdk:21-jdk-slim 

# Define el puerto interno que Spring Boot escuchará
EXPOSE 8080

# Copia el JAR compilado (Nombre: demo-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]