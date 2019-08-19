package com.gumichan01.challenge.domain.service;


import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import com.gumichan01.challenge.persistence.AuctionRepository;
import com.gumichan01.challenge.domain.service.exception.BadRequestException;
import com.gumichan01.challenge.domain.service.exception.InconsistentAuctionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTest {

    @Mock
    private AuctionHouseRepository auctionHouseRepositoryMock;

    @Mock
    private AuctionRepository auctionRepositoryMock;

    @InjectMocks
    private AuctionService auctionService;

    @Test
    public void shouldRetrieveNotAuctionFromEmptyRepository() {
        assertThat(auctionService.retrieveAuctionsBy(1L, null)).isEmpty();
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionIfTheAuctionHouseIdIsNotProvided() {
        auctionService.registerAuction(null, new AuctionDto());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionOnMalformedDTO() {
        auctionService.registerAuction(1L, new AuctionDto());
    }

    @Test(expected = InconsistentAuctionException.class)
    public void shouldThrowInconsistentAuctionExceptionDtoWithEndTimeBeforeStartingTime() {
        Calendar instance1 = Calendar.getInstance();
        Date startingTime = instance1.getTime();
        Calendar instance2 = Calendar.getInstance();
        instance2.set(Calendar.MINUTE, instance1.get(Calendar.MINUTE) - 10);
        Date endTime = instance2.getTime();
        AuctionDto dto = new AuctionDto("foo", "bar", startingTime, endTime, 42.0, 42.0);
        auctionService.registerAuction(1L, dto);
    }
}