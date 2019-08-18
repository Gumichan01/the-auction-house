package com.gumichan01.challenge.service.exception;

public class UserBidMadeByTheUserTwiceInARowException extends RuntimeException {
    public UserBidMadeByTheUserTwiceInARowException(String msg) {
        super(msg);
    }
}
