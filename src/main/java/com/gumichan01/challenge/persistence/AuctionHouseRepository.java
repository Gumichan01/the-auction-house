package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.AuctionHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionHouseRepository extends JpaRepository<AuctionHouse, Long> {
}
