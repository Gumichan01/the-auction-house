package com.gumichan01.challenge;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(AuctionHouseRepository repository) {
        return args -> {

            System.out.println("Preloading " + repository.save(new AuctionHouse("miku house")));
            System.out.println("Preloading " + repository.save(new AuctionHouse("gumi house")));
            System.out.println("Preloading " + repository.save(new AuctionHouse("luka house")));
        };
    }
}
