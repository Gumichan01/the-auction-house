package com.gumichan01.challenge.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Auction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startingTime;

    @Column(nullable = false)
    private Date endTime;

    @Column(nullable = false)
    private Double startPrice;

    @Column(nullable = false)
    private Double currentPrice;

    @OneToOne
    @JoinColumn(name = "house_id")
    private AuctionHouse auctionHouse;

    public Auction() {
    }

    public Auction(String name, String description, Date startingTime, Date endTime, Double startPrice,
                   AuctionHouse auctionHouse) {
        this.name = name;
        this.description = description;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
        this.auctionHouse = auctionHouse;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Auction))
            return false;

        Auction auction = (Auction) o;
        return Objects.equals(id, auction.id) &&
                Objects.equals(name, auction.name) &&
                Objects.equals(description, auction.description) &&
                Objects.equals(startingTime, auction.startingTime) &&
                Objects.equals(endTime, auction.endTime) &&
                Objects.equals(startPrice, auction.startPrice) &&
                Objects.equals(auctionHouse, auction.auctionHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startingTime, endTime, startPrice, auctionHouse);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startingTime=" + startingTime +
                ", endTime=" + endTime +
                ", startPrice=" + startPrice +
                ", auctionHouse=" + auctionHouse +
                '}';
    }
}
