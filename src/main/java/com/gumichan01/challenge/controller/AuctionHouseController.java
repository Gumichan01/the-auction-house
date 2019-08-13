package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuctionHouseController {

    @Autowired
    private AuctionHouseRepository repository;

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("/houses")
    public List<AuctionHouse> retrieveAuctionHouse() {
        return repository.findAll();
    }
}
