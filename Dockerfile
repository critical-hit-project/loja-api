FROM eclipse-temurin:21-jdk AS build
WORKDIR /build

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY lib/ lib/

COPY src/ src/

RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

# Porta padrão do loja-service
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]