package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuctionHouseController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHouseService.class);

    @Autowired
    private AuctionHouseService service;

    @GetMapping("/houses")
    public List<AuctionHouse> retrieveAuctionHouse() {
        return service.retrieveAllAuctionHouses();
    }

    @PostMapping("/houses")
    public ResponseEntity<AuctionHouse> registerAuctionHouse(@RequestBody AuctionHouse auctionHouse) {
        AuctionHouse registeredAuctionHouse = service.registerAuctionHouse(auctionHouse);
        return new ResponseEntity<AuctionHouse>(registeredAuctionHouse, HttpStatus.CREATED);
    }

    @DeleteMapping("/houses/{id}")
    public ResponseEntity<Void> deleteAuctionHouse(@PathVariable("id") Long id) {
        service.deleteAuctionHouse(id);
        return ResponseEntity.noContent().build();
    }
}
