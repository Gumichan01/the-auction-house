package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuctionHouseController {

    @Autowired
    private AuctionHouseService auctionHouseService;

    @GetMapping("/auction-houses")
    public List<AuctionHouse> retrieveAuctionHouse() {
        return auctionHouseService.retrieveAllAuctionHouses();
    }

    @PostMapping("/auction-houses")
    public ResponseEntity<AuctionHouse> registerAuctionHouse(@RequestBody AuctionHouse auctionHouse) {
        AuctionHouse registeredAuctionHouse = auctionHouseService.registerAuctionHouse(auctionHouse);
        return new ResponseEntity<AuctionHouse>(registeredAuctionHouse, HttpStatus.CREATED);
    }

    @DeleteMapping("/auction-houses/{id}")
    public ResponseEntity<Void> deleteAuctionHouse(@PathVariable("id") Long id) {
        auctionHouseService.deleteAuctionHouse(id);
        return ResponseEntity.noContent().build();
    }
}
