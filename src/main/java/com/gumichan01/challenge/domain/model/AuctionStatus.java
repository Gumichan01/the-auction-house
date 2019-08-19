package com.gumichan01.challenge.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AuctionStatus {
    NOT_STARTED("not_started"), RUNNING("running"), TERMINATED("terminated");

    private static final Logger logger = LoggerFactory.getLogger(AuctionStatus.class);

    AuctionStatus(String status) {
    }

    public static AuctionStatus build(String status) {
        AuctionStatus auctionStatus = null;
        try {
            if (status != null) {
                auctionStatus = AuctionStatus.valueOf(status.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        }
        return auctionStatus;
    }
}
