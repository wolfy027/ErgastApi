FROM openjdk:8
ADD target/ergastapi-spring-boot.jar ergastapi-spring-boot.jar
EXPOSE 8890
ENTRYPOINT ["java","-jar", "ergastapi-spring-boot.jar"]