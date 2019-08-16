package com.gumichan01.challenge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AuctionHouse {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    public AuctionHouse() {
    }

    public AuctionHouse(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof AuctionHouse))
            return false;

        AuctionHouse that = (AuctionHouse) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "AuctionHouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
