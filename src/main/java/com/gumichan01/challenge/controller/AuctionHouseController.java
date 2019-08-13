package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AuctionHouseController {

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("/houses")
    public List<AuctionHouse> retrieveAuctionHouse() {
        return Arrays.asList(new AuctionHouse("miku house"), new AuctionHouse("luka house"));
    }
}
