FROM openjdk:8-jdk-alpine as builder
WORKDIR /workspace/app
COPY pom.xml .
COPY mvnw .  
COPY .mvn .mvn 
COPY src ./src  
RUN ./mvnw package -DskipTests

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/app/target .
EXPOSE 8890
ENTRYPOINT ["java","-jar", "./ergastapi-spring-boot.jar"]