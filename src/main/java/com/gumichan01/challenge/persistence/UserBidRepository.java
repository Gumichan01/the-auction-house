package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.model.UserBid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserBidRepository extends CrudRepository<UserBid, Long> {

    public List<UserBid> findAll();

    @Query("SELECT u FROM UserBid u WHERE u.auction.id = ?1 ORDER BY registrationDate DESC")
    public List<UserBid> findAllSortedByDescRegistrationDateByAuctionId(Long auctionId);

    @Query("SELECT u FROM UserBid u WHERE u.auction.id in ?1")
    public Iterable<UserBid> findAllByAuctionId(Iterable<Long> auctionIds);
}
