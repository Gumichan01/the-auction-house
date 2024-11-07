# The Auction House #

Just a REST aplication made in Java with Spring that simulate an auction house.

## Build ##

You can build the project with Maven.

Compile the application:
```
./mvnw compile      # or mvn compile
```

Test:
```
./mvnw test         # or mvn test
```
If you just want to launch the app:
```
./mvnw spring-boot:run
```

## Some notes about the Web Application (not exhaustive) ##

- The format I use to send and retrieve data is in JSON.
- I use GET, POST, and DELETE as HTTP requests.
- I chose Spring (Spring Boot) to develop the application.
- Auction houses have a unique name.
- An auction house can be deleted if its auctions are not running.
- Auctions are distinct.
- An auction cannot be deleted if it is running.
- A user bid can be registered if and only if the bidding price is higher that the
auction current price and the auction is running.
- An auction house/auction cannot be added twice.
- An user cannot bid twice in row in a same auction.
- An auction house is preloaded for test purpose.


## Test the app ##

### Auction house ###

- Add an auction house:
```
curl -i -H "Content-Type: application/json" -d '{"name":"foo"}' localhost:8080/auction-houses
```

- List all auction houses:
```
curl -i localhost:8080/auction-houses
```

- Delete an auction house:
```
curl -i -X DELETE localhost:8080/auction-houses/{id}
```

### Auction ###

- List all auctions, for a given auction house:
```
curl -i localhost:8080/auction-houses/{auction_house_id}/auctions?filter=[not_started|running|terminated]
```

- Add an auction, for a given auction house:
```
curl -i -H "Content-Type: application/json" -d '{"name":"foo", "description":"bar", "starting_time":"2019-08-16", "end_time":"2019-08-16T15:45:42Z", "start_price":"42.0", "current_price":"64.0"}' localhost:8080/auction-houses/{auction_house_id}/auctions
```

NB:
1. *current_price* is optional, the other fields are mandatory.
2. The two dates must follow the [ISO-8601][1] standard. Otherwise, the request will fail.


- Delete an auction, for a given auction house:
```
curl -i -X DELETE localhost:8080/auction-houses/{auction_house_id}/auctions/{1}
```


### User bid ###

- Let a user bid, for a given auction:
```
curl -i -H "Content-Type: application/json" -d '{"name":"foo","price":"42.0"}' localhost:8080/auctions/{auction_id}/userbids
```

- List all user bids that has happened until now, for a given auction:
```
curl -i localhost:8080/auctions/{auction_id}/userbids
```
The user bids are sorted by dates, from the most recent to the oldest one.

- For a terminated auction, return the winner:
```
curl -i localhost:8080/auctions/{auction_id}/userbids/winner
```

---
[1]: https://en.wikipedia.org/wiki/ISO_8601
