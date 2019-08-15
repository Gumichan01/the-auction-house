package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.Auction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {

    // TODO Define custom methods to get auctions by house Id
    public List<Auction> findAll();
}
