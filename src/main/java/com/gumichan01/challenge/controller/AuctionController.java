package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.service.AuctionHouseService;
import com.gumichan01.challenge.service.AuctionService;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuctionController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHouseService.class);

    @Autowired
    AuctionService service;

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("/houses/auctions/{house_id}")
    public List<AuctionDto> retrieveAuctions(@PathVariable("house_id") Long houseId) {
        List<Auction> auctions = service.retrieveAuctionsBy(houseId);
        return generateDto(auctions);
    }

    @PostMapping("/houses/auctions/")
    public ResponseEntity<AuctionDto> registerAuction(@RequestBody AuctionDto auctionDto) {
        return ResponseEntity.created(URI.create("")).build();
    }

    private List<AuctionDto> generateDto(List<Auction> auctions) {
        return auctions.stream().map(AuctionDto::new).collect(Collectors.toList());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNoResourceFound(ResourceNotFoundException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}