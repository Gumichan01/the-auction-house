package com.gumichan01.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.domain.Auction;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionService;
import com.gumichan01.challenge.service.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest {

    private static final String AUCTION_URL = "/houses/auctions";

    @Mock
    private AuctionService service;

    @InjectMocks
    private AuctionController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void shouldREturnEmptyListOfAuctionsNotRelatedToAnNonExistingHouse() throws Exception {
        String expectedJsonContent = "[]";
        String AUCTION_TO_HOUSE_BY_ID_URL = AUCTION_URL + "/42";
        this.mockMvc.perform(get(AUCTION_TO_HOUSE_BY_ID_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void shouldReturnNonEmptyListAndSendHttpOk() throws Exception {
        String AUCTION_TO_HOUSE_BY_ID_URL = AUCTION_URL + "/42";
        Calendar instance = Calendar.getInstance();
        Date startTime = instance.getTime();
        instance.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = instance.getTime();
        AuctionHouse auctionHouse = new AuctionHouse("gumi house");
        double startPrice = 42.0;

        Auction auction = new Auction("gumi auction", "lorem ipsum", startTime, endTime, startPrice, auctionHouse);
        String expectedJsonContent = "[" + jsonOf(new AuctionDto(auction)) + "]";     // We expect an JSON array, not a JSON object
        when(service.retrieveAuctionsBy(42L)).thenReturn(Arrays.asList(auction));
        this.mockMvc.perform(get(AUCTION_TO_HOUSE_BY_ID_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void shouldReturnBadRequestError() throws Exception {
        this.mockMvc.perform(get(AUCTION_URL + "/null")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundResourceErrorIfTheHouseDoesNotExist() throws Exception {
        when(service.retrieveAuctionsBy(42L)).thenThrow(ResourceNotFoundException.class);
        this.mockMvc.perform(get(AUCTION_URL + "/42")).andDo(print()).andExpect(status().isNotFound());
    }

    private String jsonOf(@NonNull Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}