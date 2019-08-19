package com.gumichan01.challenge.domain.service.exception;

public class AuctionIsStartedException extends RuntimeException {
    public AuctionIsStartedException(String msg) {
        super(msg);
    }
}
