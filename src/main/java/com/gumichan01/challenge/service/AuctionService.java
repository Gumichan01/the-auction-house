package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    AuctionHouseRepository houseRepository;

    @Autowired
    AuctionRepository auctionHouseRepository;

    public List<Auction> retieveAuctions(Long houseId) {
        // TODO Check if the house exists

        if (houseId == null) {
            throw new BadRequestException("The auction house id is not provided.\n");
        }

        Optional<AuctionHouse> houseById = houseRepository.findById(houseId);

        if (!houseById.isPresent()) {
            throw new ResourceNotFoundException("Cannot retrieve auctions: the house id refers to a house that does not exist.\n");
        }

        List<Auction> auctions = auctionHouseRepository.findAll();
        List<Auction> auctionsFilteredByHouseId = auctions.stream()
                .filter(auction -> houseId.equals(auction.getAuctionHouse().getId())).collect(Collectors.toList());
        return auctionsFilteredByHouseId;
    }
}
