# Java Challenge #

## Build ##

You can build the project with Maven.

Compile and test the application
```
./mvnw compile
./mvnw test
```

Generate the package :
```
./mvnw package
```

Then run the application with:
```
java -jar target/challenge-0.0.1-SNAPSHOT.jar
```
or
```
./mvnw spring-boot:run
```

## Test the app manually ##

Add an auction house:

```
curl -H Content-Type: application/json -d '{"name":"foo"}' localhost:8080/houses
```

List all auction houses:
```
curl localhost:8080/houses
```