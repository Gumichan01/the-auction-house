package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionHouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionHouseControllerTest {

    @Mock
    private AuctionHouseService service;

    @InjectMocks
    private AuctionHouseController controller;

    @Test
    public void shouldReturnEmptyListOfAuctionHouses() throws Exception {
        when(service.retrieveAllAuctionHouses()).thenReturn(new ArrayList<>());
        assertThat(controller.retrieveAuctionHouse()).isEmpty();
    }

    @Test
    public void addAuctionHouseByProvidingItsNameAndReturnTheObjectInJsonFormat() throws Exception {
        AuctionHouse house = new AuctionHouse("test controller");
        house.setId(1L);
        when(service.registerAuctionHouse(house)).thenReturn(house);
        ResponseEntity<AuctionHouse> response = controller.registerAuctionHouse(house);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(house);
    }
}
