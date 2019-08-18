# Java Challenge #

## Build ##

You can build the project with Maven.

Compile the application:
```
./mvnw compile      # or mvn compile
```

You can test the application: 
```
./mvnw test         # or mvn test
```

Generate the package :
```
./mvnw package      # or mvn test
```

Then run the application with:
```
java -jar target/challenge-0.0.1-SNAPSHOT.jar
```
or you can simply use
```
./mvnw spring-boot:run
```

## Test the app manually ##

### Auction house ###

Add an auction house:

```
curl -i -H Content-Type: application/json -d '{"name":"foo"}' localhost:8080/auction-houses
```

List all auction houses:
```
curl -i localhost:8080/auction-houses
```

Delete an auction house:
```
curl -i -X DELETE localhost:8080/auction-houses/{id}
```

### Auction ###

List all auctions, for a given auction house:
```
curl -i localhost:8080/auction-houses/{auction_house_id}/auctions
```

Add an auction, for a given auction house:
```
curl -i -H "Content-Type: application/json" -d '{"name":"foo", "description":"bar", "starting_time":"2019-08-16", "end_time":"2019-08-16T15:45:42Z", "start_price":"42.0", "current_price":"64.0"}' localhost:8080/auction-houses/{auction_house_id}/auctions
```

NB:
1. *current_price* is optional, the other fields are mandatory.
2. The two dates must follow the [ISO-8601][1] standard. Otherwise, the request will fail.


Delete an auction, for a given auction house:
```
curl -i -X DELETE localhost:8080/auction-houses/{auction_house_id}/auctions/{1}
```


### User bid ###

Let a user bid, for a given auction:
```
curl -i -H "Content-Type: application/json" -d '{"name":"foo","price":"42.0"}' localhost:8080/auctions/{auction_id}/userbids
```

List all user bids that has happened until now, for a given auction:
```
curl -i localhost:8080/auctions/{auction_id}/userbids
```
The user bids are sorted by dates, from the most recent to the oldest one.

For a terminated auction, return the winner:
```
curl -i localhost:8080/auctions/{auction_id}/userbids/winner
```

---
[1]: https://en.wikipedia.org/wiki/ISO_8601
