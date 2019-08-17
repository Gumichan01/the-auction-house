package com.gumichan01.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBidDto {

    private Long id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private Double price;

    @JsonProperty(value = "auction_id", required = true)
    private Long auctionId;

    public UserBidDto() {
    }

    public UserBidDto(String name, Double price, Long auctionId) {
        this.name = name;
        this.price = price;
        this.auctionId = auctionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public String toString() {
        return "UserBidDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", auctionId=" + auctionId +
                '}';
    }
}
