package com.gumichan01.challenge.domain.service.exception;

public class InconsistentAuctionException extends BadRequestException {
    public InconsistentAuctionException(String msg) {
        super(msg);
    }
}
