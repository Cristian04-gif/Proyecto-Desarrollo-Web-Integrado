# --------------------------------------------------------------------------------
# Solución de una sola etapa (Más simple, pero la imagen final es más grande)
# Usamos una imagen que tiene tanto el JDK 21 como Maven.
# --------------------------------------------------------------------------------
FROM maven:3.9.6-jdk-21 

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto a la carpeta de trabajo
# Esto es necesario para que Maven pueda acceder a ellos.
COPY . . 

# Comando de compilación: Compila el código y genera el JAR ejecutable en la carpeta target/
# ¡Esta línea reemplaza la necesidad de la etapa de "build" separada!
RUN mvn clean package -DskipTests

# Mantenemos las líneas de tu código:
VOLUME /tmp
# Nota: La línea de COPY no es necesaria porque el JAR está ahora en /app/target/

EXPOSE 8080

# Comando para iniciar la aplicación (Asegúrate de cambiar el nombre del JAR)
# Usamos la ruta completa dentro del contenedor: /app/target/demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/target/demo-0.0.1-SNAPSHOT.jar"]