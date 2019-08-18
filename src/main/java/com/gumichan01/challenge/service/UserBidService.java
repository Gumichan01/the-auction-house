package com.gumichan01.challenge.service;

import com.gumichan01.challenge.controller.dto.UserBidDto;
import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.UserBid;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.persistence.UserBidRepository;
import com.gumichan01.challenge.service.exception.BadRequestException;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import com.gumichan01.challenge.service.exception.UserBidConstraintController;
import com.gumichan01.challenge.service.exception.UserBidMadeByTheUserTwiceInARowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserBidService {

    private static final Logger logger = LoggerFactory.getLogger(UserBidService.class);

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserBidRepository userBidRepository;

    public List<UserBid> retrieveUserBids(Long auctionId) {
        if (auctionId == null) {
            throw new BadRequestException("The auction id is not provided");
        }
        return userBidRepository.findAllSortedByDescRegistrationDateByAuctionId(auctionId);
    }

    public UserBid registerUserBid(Long auctionId, UserBidDto userBidDto) {
        logger.info("Let " + userBidDto);
        if (auctionId == null || userBidDto == null) {
            throw new BadRequestException("The user bid to register, and/or the id of the auction is/are not provided.\n");
        }

        Optional<Auction> auctionById = auctionRepository.findById(auctionId);
        if (!auctionById.isPresent()) {
            throw new ResourceNotFoundException("The id refers to auction that does not exist.\n");
        }

        Auction auction = auctionById.get();
        UserBid userBid = new UserBid(userBidDto.getName(), userBidDto.getPrice(), auction);
        logger.info("The user bid is related to \n" + auction);
        if (isLastBidMadeByTheSameUser(userBid)) {
            throw new UserBidMadeByTheUserTwiceInARowException("You cannot bid twice in a row.\n");
        }

        if (!isUserBidAllowed(userBid, auction)) {
            throw new UserBidConstraintController("Cannot register the user bid. The price is to low, " +
                    "or the auction is not started, or it is terminated.\n");
        }

        auction.setCurrentPrice(userBid.getPrice());
        userBid.setRegistrationDate(Calendar.getInstance().getTime());
        logger.info("User bid registered on " + userBid.getRegistrationDate());
        return userBidRepository.save(userBid);
    }

    private boolean isLastBidMadeByTheSameUser(UserBid userBid) {

        List<UserBid> userBidsByAuctionId = userBidRepository.findAllSortedByDescRegistrationDateByAuctionId(userBid.getAuction().getId());
        // For debug purpose
        if (!userBidsByAuctionId.isEmpty()) {
            UserBid lastUserBid = userBidsByAuctionId.get(0);
            logger.debug("Last bid by " + lastUserBid.getName() + " on " + lastUserBid.getRegistrationDate());
        }
        // End
        return !userBidsByAuctionId.isEmpty() && userBidsByAuctionId.get(0).getName().equals(userBid.getName());
    }

    private boolean isUserBidAllowed(UserBid userBid, Auction auction) {
        return userBid.getPrice() > auction.getCurrentPrice() && isAuctionStarted(auction) && !isTerminated(auction);
    }

    private boolean isAuctionStarted(Auction auction) {
        Instant now = Calendar.getInstance().getTime().toInstant();
        return auction.getStartingTime().toInstant().isBefore(now);
    }

    private boolean isTerminated(Auction auction) {
        Instant now = Calendar.getInstance().getTime().toInstant();
        return auction.getEndTime().toInstant().isBefore(now);
    }
}
