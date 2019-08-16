package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.Auction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuctionRepository extends CrudRepository<Auction, Long> {

    public List<Auction> findAll();

    @Query("SELECT a FROM Auction a WHERE a.auctionHouse.id = ?1")
    public List<Auction> findAllByHouseId(Long houseId);
}
