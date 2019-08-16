package com.gumichan01.challenge.service.exception;

public class InconsistentAuctionException extends BadRequestException {
    public InconsistentAuctionException(String msg) {
        super(msg);
    }
}
