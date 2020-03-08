# ErgastApi Wrapper
  This project is an Ergast API Wrapper made using Spring Boot providing the following 
  data () :-

### 1. The top 10 most victorious grand-prixs by Nationalities over a range of years
<code>
GET http://$host:$port/victories?start=2010&end=2015&type=$type
</code>


### 2. The average time of pitstops by constructors in a determined year considering a threshold
<code>
POST http://$host:$port/pitstops?type=$type
Content-Type: application/json

{
    "year":2019,
    "threshold":30
}
</code>

# Definition
- $host  : ip address/ localhost of machine
- $port  : external port to access service as defined in ./docker-compose.yml file
- $type  : type of data expected as response which is either "csv" or "json"


# About Source

### Main
- api.ergast.application.ErgastApiApplication.java
### Controller
- api.ergast.controller.ErgastServiceController.java
### Services
- api.ergast.controller.service.VictoryDataService.java
- api.ergast.controller.service.PitStopDataService.java
  

# Docker Setup :

### Run following docker-compose commands to get the docker container starting . 
- docker-compose build
- docker-compose up


# Test Execution
- Change test input values in *./src/main/resources/application-test.properties*
- <code>mvn test</code>