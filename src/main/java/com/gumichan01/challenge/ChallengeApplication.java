package com.gumichan01.challenge;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
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
    public CommandLineRunner index(AuctionRepository auctionRepository, AuctionHouseRepository houseRepository) {
        return args -> {
            Calendar instance = Calendar.getInstance();
            Date startTime = instance.getTime();
            instance.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = instance.getTime();
            AuctionHouse auctionHouse = new AuctionHouse("gumi house");
            double startPrice = 42.0;
            houseRepository.save(auctionHouse);
            AuctionHouse auctionHouse1 = new AuctionHouse("l");
            houseRepository.save(auctionHouse1);
            AuctionHouse auctionHouse2 = new AuctionHouse("g");
            houseRepository.save(auctionHouse2);
            auctionRepository.save(new Auction("gumi auction", "lorem ipsum",startTime, endTime, startPrice, auctionHouse));
            auctionRepository.save(new Auction("baka auction", "lorem",startTime, endTime, startPrice, auctionHouse1));
            auctionRepository.save(new Auction("lalalala", "lorem",startTime, endTime, startPrice, auctionHouse2));
        };
    }
}
