# -----------------------------
# ETAPA 1: BUILD (Compilación)
# Utilizamos una etiqueta de Maven/JDK 21 muy común.
# -----------------------------
FROM maven:3.9-jdk-21 AS build
# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de configuración y código
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el JAR ejecutable.
RUN mvn clean package -DskipTests

# -----------------------------
# ETAPA 2: RUN (Ejecución)
# Utilizamos una imagen JRE 21 LTS basada en Ubuntu Focal (muy estable).
# -----------------------------
FROM eclipse-temurin:21-jre-focal

# Define el puerto interno que Spring Boot escuchará
EXPOSE 8080

# Copia el JAR compilado (Nombre: demo-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]