#### CREACION DEL JAR ####
FROM maven:3-openjdk-17-slim AS builder

WORKDIR /app
COPY ./pom.xml .
RUN mvn -e -B dependency:go-offline
COPY ./src ./src
RUN mvn -e -B -D maven.test.skip=true package


#### EJECUCION DEL JAR ####
FROM openjdk:17-slim

WORKDIR /workspace

#RUN apt update && apt install wget -y && apt clean

COPY --from=builder /app/target/kafka-stream-*.jar app.jar

ENTRYPOINT exec java -Dspring.profiles.active=cloud -jar /workspace/app.jar
