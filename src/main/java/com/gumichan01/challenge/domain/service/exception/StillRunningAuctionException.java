package com.gumichan01.challenge.domain.service.exception;

public class StillRunningAuctionException extends RuntimeException {
    public StillRunningAuctionException(String msg) {
        super(msg);
    }
}
