package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.controller.dto.UserBidDto;
import com.gumichan01.challenge.domain.UserBid;
import com.gumichan01.challenge.service.UserBidService;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.AuctionHouseConstraintViolationException;
import com.gumichan01.challenge.service.exception.UserBidMadeByTheUserTwiceInARowException;
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
    UserBidService service;

    @GetMapping("/")
    public String index() {
        return "Challenge accepted - It works!";
    }

    @GetMapping("/userbids/")
    public List<UserBidDto> retrieveUserBids() {
        List<UserBid> userBids = service.retrieveUserBids();
        return generateDto(userBids);
    }

    @PostMapping("/userbids/")
    public ResponseEntity<UserBidDto> registerUserBid(@RequestBody UserBidDto userBidDto) {
        UserBid userBid = service.registerUserBid(userBidDto);
        return new ResponseEntity<>(new UserBidDto((userBid)), HttpStatus.CREATED);
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
