package com.gumichan01.challenge;

import com.gumichan01.challenge.domain.model.Auction;
import com.gumichan01.challenge.domain.model.AuctionHouse;
import com.gumichan01.challenge.domain.model.UserBid;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.persistence.UserBidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.Date;


@SpringBootApplication
public class ChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Bean
    public CommandLineRunner preloadAuctionHousesFrom(AuctionHouseRepository auctionHouseRepository) {
        return args -> {
            AuctionHouse auctionHouse = new AuctionHouse("gumi auction house");
            auctionHouseRepository.save(auctionHouse);
        };
    }
}
