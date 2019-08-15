package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.Auction;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
}
