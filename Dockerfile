# Usa la imagen oficial de Eclipse Temurin (Java 21)
FROM eclipse-temurin:21-jdk-jammy AS build
COPY . .
# Ajusta según tu gestor de dependencias (Maven en este caso)
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar
# Java 21 tiene optimizaciones de memoria muy buenas para contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-jar", "/app.jar"]