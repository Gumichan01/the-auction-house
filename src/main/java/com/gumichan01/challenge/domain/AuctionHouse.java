package com.gumichan01.challenge.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AuctionHouse {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public AuctionHouse() {
    }

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

    @Override
    public String toString() {
        return "AuctionHouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
