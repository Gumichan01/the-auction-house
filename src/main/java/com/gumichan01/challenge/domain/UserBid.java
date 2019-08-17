package com.gumichan01.challenge.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class UserBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @OneToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public UserBid() {
    }

    public UserBid(String name, Double price, Auction auction) {
        this.name = name;
        this.price = price;
        this.auction = auction;
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

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBid)) return false;
        UserBid userBid = (UserBid) o;
        return Objects.equals(name, userBid.name) &&
                Objects.equals(price, userBid.price) &&
                Objects.equals(auction, userBid.auction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, auction);
    }

    @Override
    public String toString() {
        return "UserBid{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", auction=" + auction +
                '}';
    }
}
