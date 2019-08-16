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

### Auction house ###

Add an auction house:

```
curl -H Content-Type: application/json -d '{"name":"foo"}' localhost:8080/houses
```

List all auction houses:
```
curl localhost:8080/houses
```

Delete an auction house:
```
curl -X DELETE localhost:8080/houses/{id}
```

### Auction ###

List all auctions associated to a house:
```
curl localhost:8080/houses/auctions/{house_id}
```

Add an auction related to an auction house (by id):
```
curl -H Content-Type: application/json -d '{"name":"foo", "description":"bar", "starting_time":"2019-08-16", "end_time":"2019-08-16T15:45:42Z", "start_price":"42.0", "current_price":"64.0", "house_id":"1"}' localhost:8080/houses/auctions/
```

NB:
1. *current_price* is optional, the other fields are mandatory.
2. The two dates must follow the [ISO-8601][1] standard. Otherwise, the request will fail.

---
[1]: https://en.wikipedia.org/wiki/ISO_8601
