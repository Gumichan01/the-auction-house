package com.gumichan01.challenge.service;

import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.service.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

    @Autowired
    AuctionHouseRepository houseRepository;

    @Autowired
    AuctionRepository auctionRepository;

    public List<Auction> retrieveAuctionsBy(Long houseId) {
        logger.info("Get auctions from house by this id: " + houseId);
        if (houseId == null) {
            throw new BadRequestException("The auction house id is not provided.\n");
        }

        return auctionRepository.findAllByHouseId(houseId);
    }

    public void deleteAuction(Long id) {
        logger.info("delete auction by id: " + id);
        if (id == null) {
            throw new BadRequestException("Invalid request: no identifier provided.\n");
        }

        Optional<Auction> auctionById = auctionRepository.findById(id);
        if (!auctionById.isPresent()) {
            throw new ResourceNotFoundException("Auction to delete not found.\n");
        }

        if (isProcessing(auctionById.get())) {
            throw new AuctionConstraintViolationException("This auction is already started. Wait until it is terminated to delete it.\n");
        }

        logger.info("delete the auction: " + auctionById.get());
        auctionRepository.deleteById(id);
    }

    private boolean isProcessing(Auction auction) {
        Date now = Calendar.getInstance().getTime();
        return inDateInterval(Calendar.getInstance().getTime(), auction.getStartingTime(), auction.getEndTime());
    }

    public Auction registerAuction(AuctionDto auctionDto) {
        logger.info("auction param: ");
        logger.info(auctionDto.toString());

        if (!isValidAuctionDto(auctionDto)) {
            throw new BadRequestException("Invalid DTO: " + auctionDto + ".\n");
        }

        if (!isConsistent(auctionDto)) {
            throw new InconsistentAuctionException("Invalid period of times.\n" +
                    "Expected: starting_time < end_time; " +
                    "\ngot: (starting_time = " + auctionDto.getStartingTime() +
                    ") >= (end_time = " + auctionDto.getEndTime() + ").\n");
        }

        AuctionHouse house = getAuctionHouseBy(auctionDto.getHouseId());
        logger.info("house related to the auction: ");
        logger.info(house.toString());
        Auction auctionToSave = new Auction(auctionDto);
        auctionToSave.setAuctionHouse(house);
        auctionToSave.setCurrentPrice(null);

        List<Auction> auctionsInConflictWithCurrrentAuction = findAuctionsInConflictWithCurrentAuction(house, auctionToSave);
        if (!auctionsInConflictWithCurrrentAuction.isEmpty()) {
            logger.warn("Oups, " + auctionsInConflictWithCurrrentAuction.size() + " auction(s) in conflict");
            throw new InconsistentAuctionException("The auction to register\n -" + auctionToSave +
                    "- has a duration that overlap one or several auction(s) related to the same house.\n");
        }

        logger.info("save " + auctionToSave);
        return auctionRepository.save(auctionToSave);
    }

    private List<Auction> findAuctionsInConflictWithCurrentAuction(AuctionHouse house, Auction auctionToSave) {
        List<Auction> auctionsByHouseId = auctionRepository.findAllByHouseId(house.getId());
        if (auctionsByHouseId.contains(auctionToSave)) {
            throw new AlreadyRegisteredException("The auction is already registered.\n");
        }

        return auctionsByHouseId.stream()
                .filter(auction -> hasOverlappingTimeInterval(auction, auctionToSave)).collect(Collectors.toList());
    }

    private AuctionHouse getAuctionHouseBy(Long houseId) {
        Optional<AuctionHouse> houseById = houseRepository.findById(houseId);
        if (!houseById.isPresent()) {
            throw new ResourceNotFoundException("Cannot create the auction. The house to link with does not exist.\n");
        }

        return houseById.get();
    }

    // No need to check the dates to null, because it must exists when the auction is created. This is a business constraint.
    private boolean hasOverlappingTimeInterval(Auction auction, Auction auctionToSave) {
        return inDateInterval(auction.getEndTime(), auctionToSave.getStartingTime(), auctionToSave.getEndTime()) ||
                inDateInterval(auctionToSave.getEndTime(), auction.getStartingTime(), auction.getEndTime());
    }

    private boolean inDateInterval(Date refTime, Date startingTime, Date endTime) {
        Instant instant = refTime.toInstant();
        Instant startInstant = startingTime.toInstant();
        Instant endInstant = endTime.toInstant();
        return startInstant.isBefore(instant) && endInstant.isAfter(instant);
    }

    private boolean isConsistent(AuctionDto auctionDto) {
        Date auctionStartingTime = auctionDto.getStartingTime();
        Date auctionEndTime = auctionDto.getEndTime();
        return auctionEndTime.after(auctionStartingTime);
    }

    private boolean isValidAuctionDto(AuctionDto auctionDto) {
        return auctionDto != null && auctionDto.getName() != null && auctionDto.getDescription() != null
                && auctionDto.getStartingTime() != null && auctionDto.getEndTime() != null
                && auctionDto.getHouseId() != null;
    }
}
