package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.exception.AlreadyExistException;
import com.gumichan01.challenge.service.AuctionHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuctionHouseController {

    @Autowired
    private AuctionHouseService service;

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("/houses")
    public List<AuctionHouse> retrieveAuctionHouse() {
        return service.retrieveAllAuctionHouses();
    }

    @PostMapping("/houses")
    public ResponseEntity<AuctionHouse> registerAuctionHouse(@RequestBody AuctionHouse auctionHouse) {
        AuctionHouse registeredAuctionHouse = service.registerAuctionHouse(auctionHouse);
        return new ResponseEntity<AuctionHouse>(registeredAuctionHouse, HttpStatus.CREATED);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String> handleError(AlreadyExistException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
