package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.UserBid;
import com.gumichan01.challenge.persistence.UserBidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBidService {

    @Autowired
    UserBidRepository userBidRepository;

    public List<UserBid> retrieveUserBids() {
        return userBidRepository.findAll();
    }
}
