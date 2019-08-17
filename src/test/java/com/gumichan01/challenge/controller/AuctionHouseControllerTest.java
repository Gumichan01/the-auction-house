package com.gumichan01.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumichan01.challenge.domain.AuctionHouse;
import com.gumichan01.challenge.service.AuctionHouseService;
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
    public void deleteAuctionHouseByIdAndGetNoContent() throws Exception {

        String deleteUrl = AUCTION_HOUSES_URL + "/1";
        AuctionHouse mockHouse = new AuctionHouse("mock house");
        // Since the id of the house is generated in the application in production, I must manually provide it
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
