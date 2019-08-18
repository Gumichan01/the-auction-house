package com.gumichan01.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gumichan01.challenge.domain.UserBid;

import java.util.Date;

public class UserBidDto {

    private Long id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private Double price;

    @JsonProperty(value = "registration_date")
    private Date registrationDate;

    public UserBidDto() {
    }

    public UserBidDto(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UserBidDto(UserBid userBid) {
        this.id = userBid.getId();
        this.name = userBid.getName();
        this.price = userBid.getPrice();
        this.registrationDate = userBid.getRegistrationDate();
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "UserBidDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
