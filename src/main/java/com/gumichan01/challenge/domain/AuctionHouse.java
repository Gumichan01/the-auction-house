package com.gumichan01.challenge.domain;

public class AuctionHouse {

    private Long id = 1L; // Just to simplify
    private String name;

    public AuctionHouse(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
