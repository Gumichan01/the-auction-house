package com.gumichan01.challenge.service;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.persistence.AuctionHouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionHouseServiceTest {

    @Mock
    private AuctionHouseRepository repositoryMock;

    @InjectMocks
    private AuctionHouseService service;

    @Test
    public void shouldRetrieveNotAuctionHouseFromEmptyRepository() {
        when(repositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThat(service.retrieveAllAuctionHouses()).isEmpty();
    }

    @Test
    public void shouldReturnRegisteredAuctionHouse() {
        final AuctionHouse auctionHouse = new AuctionHouse("mock house");
        when(repositoryMock.save(auctionHouse)).thenReturn(auctionHouse);
        assertThat(service.registerAuctionHouse(auctionHouse)).isEqualTo(auctionHouse);
    }
}