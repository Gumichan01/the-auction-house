package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.service.AuctionHouseService;
import com.gumichan01.challenge.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuctionController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHouseService.class);

    @Autowired
    AuctionService service;

    // TODO bonus, list auctions based on their status - not started, running, terminated

    @GetMapping("/houses/auctions/{house_id}")
    public List<AuctionDto> retrieveAuctions(@PathVariable("house_id") Long houseId) {
        List<Auction> auctions = service.retrieveAuctionsBy(houseId);
        return generateDto(auctions);
    }

    @PostMapping("/houses/auctions/")
    public ResponseEntity<AuctionDto> registerAuction(@RequestBody AuctionDto auctionDto) {
        Auction auction = service.registerAuction(auctionDto);
        return new ResponseEntity<>(new AuctionDto(auction), HttpStatus.CREATED);
    }

    @DeleteMapping("/houses/auctions/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        service.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    private List<AuctionDto> generateDto(List<Auction> auctions) {
        return auctions.stream().map(AuctionDto::new).collect(Collectors.toList());
    }
}
