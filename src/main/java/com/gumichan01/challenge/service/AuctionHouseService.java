package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionHouseService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHouseService.class);

    @Autowired
    private AuctionHouseRepository repository;

    public List<AuctionHouse> retrieveAllAuctionHouses() {
        return repository.findAll();
    }

    public AuctionHouse registerAuctionHouse(AuctionHouse auctionHouse) {
        AuctionHouse house = repository.findByName(auctionHouse.getName());
        if (house != null) {
            throw new AlreadyRegisteredException("The house is already registered.\n");
        }
        logger.info("register " + auctionHouse.getName());

        if (auctionHouse.getName() == null) {
            throw new BadRequestException("The house must have a name.\n");
        }

        return repository.save(auctionHouse);
    }

    public void deleteAuctionHouse(Long id) {
        logger.info("delete by id: " + id);

        if (id == null) {
            throw new BadRequestException("Invalid request: no identifier provided.\n");
        }

        Optional<AuctionHouse> optionalAuctionHouse = repository.findById(id);
        if (!optionalAuctionHouse.isPresent()) {
            throw new ResourceNotFoundException("Auction house to delete not found.\n");
        }

        logger.info("delete the auction house: " + optionalAuctionHouse.get());
        repository.deleteById(id);
    }
}
