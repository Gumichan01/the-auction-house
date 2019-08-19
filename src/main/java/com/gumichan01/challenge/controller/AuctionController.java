package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.domain.model.Auction;
import com.gumichan01.challenge.domain.service.AuctionHouseService;
import com.gumichan01.challenge.domain.service.AuctionService;
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
    private AuctionService auctionService;

    @GetMapping("/auction-houses/{auction_house_id}/auctions")
    public List<AuctionDto> retrieveAuctions(@PathVariable("auction_house_id") Long auctionHouseId,
                                             @RequestParam(value = "filter", required = false) String status) {
        List<Auction> auctions = auctionService.retrieveAuctionsBy(auctionHouseId, status);
        return generateDto(auctions);
    }

    @PostMapping("/auction-houses/{auction_house_id}/auctions")
    public ResponseEntity<AuctionDto> registerAuction(@PathVariable("auction_house_id") Long auctionHouseId,
                                                      @RequestBody AuctionDto auctionDto) {
        Auction auction = auctionService.registerAuction(auctionHouseId, auctionDto);
        return new ResponseEntity<>(new AuctionDto(auction), HttpStatus.CREATED);
    }

    @DeleteMapping("/auction-houses/{auction_house_id}/auctions/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable("auction_house_id") Long auctionHouseId,
                                              @PathVariable("id") Long id) {
        auctionService.deleteAuction(auctionHouseId, id);
        return ResponseEntity.noContent().build();
    }

    private List<AuctionDto> generateDto(List<Auction> auctions) {
        return auctions.stream().map(AuctionDto::new).collect(Collectors.toList());
    }
}
