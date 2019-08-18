package com.gumichan01.challenge.service.exception;

public class AuctionIsStartedException extends RuntimeException {
    public AuctionIsStartedException(String msg) {
        super(msg);
    }
}
