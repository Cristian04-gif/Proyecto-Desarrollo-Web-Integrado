# ============================
# 1) BUILD STAGE
# ============================
FROM eclipse-temurin:24-jdk AS build

WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Permitir ejecutar Maven Wrapper
RUN chmod +x mvnw

# Compilar el proyecto sin tests
RUN ./mvnw -DskipTests clean package


# ============================
# 2) RUN STAGE
# ============================
FROM eclipse-temurin:24-jre

WORKDIR /app

# Copiar el jar ya compilado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
