FROM openjdk:8-jdk-alpine
WORKDIR /workspace/app
COPY pom.xml .
COPY mvnw .  
COPY .mvn .mvn
#RUN ./mvnw dependency:go-offline -B 
COPY src ./src  
RUN ./mvnw clean package -DskipTests

FROM openjdk:8-jre-alpine
ADD target ./target
EXPOSE 8890
ENTRYPOINT ["java","-jar", "./target/ergastapi-spring-boot.jar"]