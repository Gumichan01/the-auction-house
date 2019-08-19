package com.gumichan01.challenge.domain.service.exception;

public class UserBidMadeByTheUserTwiceInARowException extends RuntimeException {
    public UserBidMadeByTheUserTwiceInARowException(String msg) {
        super(msg);
    }
}
