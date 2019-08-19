package com.gumichan01.challenge.domain.model;

import com.gumichan01.challenge.controller.dto.AuctionDto;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startingTime;

    @Column(nullable = false)
    private Date endTime;

    @Column(nullable = false)
    private Double startPrice;

    private Double currentPrice;

    @OneToOne
    @JoinColumn(name = "house_id")
    private AuctionHouse auctionHouse;

    public Auction() {
    }

    public Auction(String productName, String description, Date startingTime, Date endTime, Double startPrice,
                   AuctionHouse auctionHouse) {
        this.productName = productName;
        this.description = description;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
        this.auctionHouse = auctionHouse;
    }

    public Auction(AuctionDto auctionDto) {
        assert auctionDto != null;
        this.productName = auctionDto.getName();
        this.description = auctionDto.getDescription();
        this.startingTime = auctionDto.getStartingTime();
        this.endTime = auctionDto.getEndTime();
        this.startPrice = auctionDto.getStartPrice();
        this.currentPrice = auctionDto.getCurrentPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }

    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Auction))
            return false;

        Auction auction = (Auction) o;
        return Objects.equals(productName, auction.productName) &&
                Objects.equals(description, auction.description) &&
                Objects.equals(startingTime, auction.startingTime) &&
                Objects.equals(endTime, auction.endTime) &&
                Objects.equals(startPrice, auction.startPrice) &&
                Objects.equals(auctionHouse, auction.auctionHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, description, startingTime, endTime, startPrice, auctionHouse);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", name='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", startingTime=" + startingTime +
                ", endTime=" + endTime +
                ", startPrice=" + startPrice +
                ", currentPrice=" + currentPrice +
                ", auctionHouse=" + auctionHouse +
                '}';
    }
}
