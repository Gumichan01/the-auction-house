package com.gumichan01.challenge.persistence;

import com.gumichan01.challenge.domain.UserBid;
import org.springframework.data.repository.CrudRepository;

public interface UserBidRepository extends CrudRepository<UserBid, Long> {
}
