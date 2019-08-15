package com.gumichan01.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionHouseService;
import com.gumichan01.challenge.service.exception.AlreadyRegisteredException;
import com.gumichan01.challenge.service.exception.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AuctionHouseControllerTest {

    private static final String AUCTION_HOUSES_URL = "/houses";

    @Mock
    private AuctionHouseService service;

    @InjectMocks
    private AuctionHouseController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnEmptyListOfAuctionHouses() throws Exception {
        String expectedJsonContent = "[]";
        this.mockMvc.perform(get(AUCTION_HOUSES_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void addAuctionHouseByProvidingItsNameAndReturnTheObjectInJsonFormat() throws Exception {
        String jsonRequestContent = "{\"name\":\"mock house\"}";
        AuctionHouse mockHouse = new AuctionHouse("mock house");

        when(service.registerAuctionHouse(mockHouse)).thenReturn(mockHouse);
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldNotRegisterAuctionHouseWithoutName() throws Exception {
        AuctionHouse auctionHouse = new AuctionHouse(null);
        String jsonRequestContent = "{\"name\":null}";

        when(service.registerAuctionHouse(auctionHouse)).thenThrow(new BadRequestException("mock bad request"));
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotRegisterTheAuctionHouseTwice() throws Exception {
        AuctionHouse mockHouse = new AuctionHouse("mock house");
        String jsonRequestContent = "{\"name\":\"mock house\"}";

        when(service.registerAuctionHouse(mockHouse)).thenReturn(mockHouse);
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andExpect(status().isCreated());

        // This must fail
        when(service.registerAuctionHouse(mockHouse)).thenThrow(new AlreadyRegisteredException("mock registration failed"));
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andDo(print()).andExpect(status().isConflict());

    }

    @Test
    public void deleteAuctionHouseByIdAndGetNoContent() throws Exception {

        String deleteUrl = AUCTION_HOUSES_URL + "/1";
        AuctionHouse mockHouse = new AuctionHouse("mock house");
        // Since the id of the house is generated in the application in production, I must manually provide it
        mockHouse.setId(1L);
        String jsonRequestContent = jsonOf(mockHouse);

        // Add the house first in order to delete it
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent)).andDo(print());
        this.mockMvc.perform(delete(deleteUrl).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isNoContent());
    }

    private String jsonOf(@NonNull Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
