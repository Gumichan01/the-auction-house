package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.service.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

    @Autowired
    AuctionRepository auctionRepository;

    public List<Auction> retrieveAuctionsBy(Long houseId) {

        logger.info("Get auctions from house by this id: " + houseId);
        if (houseId == null) {
            throw new BadRequestException("The auction house id is not provided.\n");
        }

        return auctionRepository.findAllByHouseId(houseId);
    }
}
