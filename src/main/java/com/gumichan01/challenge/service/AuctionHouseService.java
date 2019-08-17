package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.AuctionHouseConstraintViolationException;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionHouseService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionHouseService.class);

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionHouseRepository houseRepository;

    public List<AuctionHouse> retrieveAllAuctionHouses() {
        return houseRepository.findAll();
    }

    public AuctionHouse registerAuctionHouse(AuctionHouse auctionHouse) {
        AuctionHouse house = houseRepository.findByName(auctionHouse.getName());
        if (house != null) {
            throw new AlreadyRegisteredException("The house is already registered.\n");
        }
        logger.info("register " + auctionHouse.getName());

        if (auctionHouse.getName() == null) {
            throw new BadRequestException("The house must have a name.\n");
        }

        return houseRepository.save(auctionHouse);
    }

    public void deleteAuctionHouse(Long id) {
        logger.info("delete by id: " + id);

        if (id == null) {
            throw new BadRequestException("Invalid request: no identifier provided.\n");
        }

        Optional<AuctionHouse> optionalAuctionHouse = houseRepository.findById(id);
        if (!optionalAuctionHouse.isPresent()) {
            throw new ResourceNotFoundException("Auction house to delete not found.\n");
        }

        AuctionHouse auctionHouse = optionalAuctionHouse.get();
        List<Auction> auctionsByHouseId = auctionRepository.findAllByHouseId(auctionHouse.getId());
        if (hasProcessingAuctions(auctionHouse, auctionsByHouseId)) {
            throw new AuctionHouseConstraintViolationException("Cannot delete the house. Some auctions are started. Wait until this auction is terminated to delete it.\n");
        }

        logger.info("Delete auctions related to this house");
        auctionsByHouseId.forEach(auction -> logger.info(auction.toString()));
        auctionRepository.deleteAll(auctionsByHouseId);
        logger.info("delete the auction house: " + auctionHouse);
        houseRepository.deleteById(id);
    }

    public boolean hasProcessingAuctions(AuctionHouse auctionHouse, List<Auction> auctionsByHouseId) {
        logger.info("auctions on this house: " + auctionsByHouseId.size());
        List<Auction> auctionsAlreadyStarted = auctionsByHouseId.stream()
                .filter(auction -> {
                    Instant now = Calendar.getInstance().getTime().toInstant();
                    return auction.getStartingTime().toInstant().isBefore(now) && auction.getEndTime().toInstant().isAfter(now);
                }).collect(Collectors.toList());

        logger.info("auctions started on this house: " + auctionsAlreadyStarted.size());
        return !auctionsAlreadyStarted.isEmpty();
    }
}
