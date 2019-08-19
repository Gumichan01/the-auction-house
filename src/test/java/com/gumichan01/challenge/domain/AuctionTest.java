package com.gumichan01.challenge.domain;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AuctionTest {

    @Test
    public void shouldHaveTheCurrentPriceEqualToTheStartPrice() {

        Calendar instance = Calendar.getInstance();
        Date startTime = instance.getTime();
        instance.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = instance.getTime();
        AuctionHouse auctionHouse = new AuctionHouse("gumi house");
        double startPrice = 1.0;
        Auction auction = new Auction("hello", "lorem ipsum", startTime, endTime, startPrice, auctionHouse);
        assertThat(auction.getCurrentPrice()).isEqualTo(startPrice);
    }
}