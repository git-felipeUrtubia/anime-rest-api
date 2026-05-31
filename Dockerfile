# Usa la imagen oficial de Eclipse Temurin (Java 21)
FROM eclipse-temurin:21-jdk-jammy AS build
COPY . .

# LE DA PERMISOS DE EJECUCIÓN AL WRAPPER DE MAVEN
RUN chmod +x mvnw

# Ajusta según tu gestor de dependencias
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar
# Java 21 tiene optimizaciones de memoria muy buenas para contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-jar", "/app.jar"]