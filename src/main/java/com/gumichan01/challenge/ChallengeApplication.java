package com.gumichan01.challenge;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.domain.UserBid;
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
    public CommandLineRunner index(AuctionRepository auctionRepository, AuctionHouseRepository houseRepository, UserBidRepository userBidRepository) {
        return args -> {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_MONTH, -1);
            Date startTime = instance.getTime();
            instance.add(Calendar.DAY_OF_MONTH, 4);
            Date endTime = instance.getTime();
            AuctionHouse auctionHouse = new AuctionHouse("gumi house");
            double startPrice = 42.0;
            houseRepository.save(auctionHouse);
            AuctionHouse auctionHouse1 = new AuctionHouse("luka house");
            houseRepository.save(auctionHouse1);
            AuctionHouse auctionHouse2 = new AuctionHouse("teto house");
            houseRepository.save(auctionHouse2);
            Auction gumiAuction = new Auction("gumi auction", "lorem ipsum", startTime, endTime, startPrice, auctionHouse);
            auctionRepository.save(gumiAuction);
            auctionRepository.save(new Auction("baka auction", "lorem",startTime, endTime, startPrice, auctionHouse1));
            auctionRepository.save(new Auction("lalalala", "lorem",startTime, endTime, startPrice, auctionHouse2));
            userBidRepository.save(new UserBid("luno", 128.0, gumiAuction));
            userBidRepository.save(new UserBid("leon", 256.0, gumiAuction));
        };
    }
}
