package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.controller.dto.UserBidDto;
import com.gumichan01.challenge.domain.model.UserBid;
import com.gumichan01.challenge.domain.service.UserBidService;
import com.gumichan01.challenge.domain.service.exception.UserBidMadeByTheUserTwiceInARowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserBidController {

    private static final Logger logger = LoggerFactory.getLogger(UserBidController.class);

    @Autowired
    private UserBidService userBidService;

    // TODO remove it
    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("auctions/{auction_id}/userbids")
    public List<UserBidDto> retrieveUserBids(@PathVariable("auction_id") Long auctionId) {
        List<UserBid> userBids = userBidService.retrieveUserBids(auctionId);
        return generateDto(userBids);
    }

    @GetMapping("auctions/{auction_id}/userbids/winner")
    public ResponseEntity<UserBidDto> retrieveAuctionWinner(@PathVariable("auction_id") Long auctionId) {
        UserBid userBid = userBidService.retrieveAuctionWinner(auctionId);
        return new ResponseEntity<>(new UserBidDto(userBid), HttpStatus.OK);
    }

    @PostMapping("auctions/{auction_id}/userbids")
    public ResponseEntity<UserBidDto> registerUserBid(@PathVariable("auction_id") Long auctionId, @RequestBody UserBidDto userBidDto) {
        UserBid userBid = userBidService.registerUserBid(auctionId, userBidDto);
        return new ResponseEntity<>(new UserBidDto(userBid), HttpStatus.CREATED);
    }

    private List<UserBidDto> generateDto(List<UserBid> userBids) {
        return userBids.stream().map(UserBidDto::new).collect(Collectors.toList());
    }

    @ExceptionHandler(UserBidMadeByTheUserTwiceInARowException.class)
    public ResponseEntity<String> handleError(Exception e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
