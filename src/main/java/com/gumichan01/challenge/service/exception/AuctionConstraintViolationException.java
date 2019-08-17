package com.gumichan01.challenge.service.exception;

public class AuctionConstraintViolationException extends RuntimeException {
    public AuctionConstraintViolationException(String msg) {
        super(msg);
    }
}
