package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.UserBid;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserBidRepository extends CrudRepository<UserBid, Long> {

    List<UserBid> findAll();
}
