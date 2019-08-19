package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.domain.UserBid;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.persistence.UserBidRepository;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import com.gumichan01.challenge.service.exception.StillRunningAuctionException;
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
    private UserBidRepository userBidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionHouseRepository auctionHouseRepository;

    public List<AuctionHouse> retrieveAllAuctionHouses() {
        return auctionHouseRepository.findAll();
    }

    public AuctionHouse registerAuctionHouse(AuctionHouse auctionHouse) {
        AuctionHouse house = auctionHouseRepository.findByName(auctionHouse.getName());
        if (house != null) {
            throw new AlreadyRegisteredException("The auction house is already registered.\n");
        }
        logger.info("Register " + auctionHouse.getName());

        if (auctionHouse.getName() == null) {
            throw new BadRequestException("The auction house must have a name.\n");
        }

        return auctionHouseRepository.save(auctionHouse);
    }

    public void deleteAuctionHouse(Long id) {
        logger.info("delete by id: " + id);

        if (id == null) {
            throw new BadRequestException("Invalid request: no identifier provided.\n");
        }

        Optional<AuctionHouse> optionalAuctionHouse = auctionHouseRepository.findById(id);
        if (!optionalAuctionHouse.isPresent()) {
            throw new ResourceNotFoundException("Auction house to delete not found.\n");
        }

        AuctionHouse auctionHouse = optionalAuctionHouse.get();
        List<Auction> auctionsByHouseId = auctionRepository.findAllByAuctionHouseId(auctionHouse.getId());
        if (hasRunningAuctions(auctionsByHouseId)) {
            throw new StillRunningAuctionException("Cannot delete the auction house. Some auctions are running. " +
                    "Wait until those auctions are terminated to delete it.\n");
        }

        Iterable<UserBid> userBidsByAuctionId = retrieveUserBidsByAuctionsToDelete(auctionsByHouseId);
        logger.info("Delete user bids related to every auctions");
        userBidsByAuctionId.forEach(userBid -> logger.info(userBid.toString()));
        userBidRepository.deleteAll(userBidsByAuctionId);

        logger.info("Delete auctions of the auction house");
        auctionsByHouseId.forEach(auction -> logger.info(auction.toString()));
        auctionRepository.deleteAll(auctionsByHouseId);

        logger.info("delete the auction house: " + auctionHouse);
        auctionHouseRepository.deleteById(id);
    }

    private Iterable<UserBid> retrieveUserBidsByAuctionsToDelete(List<Auction> auctionsByHouseId) {
        List<Long> auctionIds = auctionsByHouseId.stream().map(Auction::getId).collect(Collectors.toList());
        return userBidRepository.findAllByAuctionId(auctionIds);
    }

    private boolean hasRunningAuctions(List<Auction> auctionsByAuctionHouseId) {
        logger.info("auctions related to this auction house: " + auctionsByAuctionHouseId.size());
        List<Auction> auctionsAlreadyStarted = auctionsByAuctionHouseId.stream()
                .filter(auction -> {
                    Instant now = Calendar.getInstance().getTime().toInstant();
                    return auction.getStartingTime().toInstant().isBefore(now) && auction.getEndTime().toInstant().isAfter(now);
                }).collect(Collectors.toList());

        logger.info("auctions started on this house: " + auctionsAlreadyStarted.size());
        return !auctionsAlreadyStarted.isEmpty();
    }
}
