FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests -pl payment-service -am

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/payment-service/target/*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]