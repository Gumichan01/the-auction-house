package com.gumichan01.challenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionHouseController {

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }
}
