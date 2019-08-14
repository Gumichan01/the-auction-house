package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionHouseService {

    @Autowired
    private AuctionHouseRepository repository;

    public List<AuctionHouse> retrieveAllAuctionHouses() {
        return repository.findAll();
    }

    public AuctionHouse registerAuctionHouse(AuctionHouse auctionHouse) {
        AuctionHouse house = repository.findByName(auctionHouse.getName());
        if (house != null) {
            throw new AlreadyExistException("The house is already registered");
        }
        return repository.save(auctionHouse);
    }
    // TODO delete a house
}
