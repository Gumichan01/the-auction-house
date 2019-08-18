package com.gumichan01.challenge.service.exception;

public class StillRunningAuctionException extends RuntimeException {
    public StillRunningAuctionException(String msg) {
        super(msg);
    }
}
