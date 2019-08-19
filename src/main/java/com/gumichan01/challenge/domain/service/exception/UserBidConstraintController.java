package com.gumichan01.challenge.domain.service.exception;

public class UserBidConstraintController extends BadRequestException {
    public UserBidConstraintController(String msg) {
        super(msg);
    }
}
