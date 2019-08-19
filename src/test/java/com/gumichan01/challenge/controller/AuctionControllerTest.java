package com.gumichan01.challenge.controller;

import com.gumichan01.challenge.domain.service.AuctionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AuctionControllerTest {

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
    public void shouldReturnEmptyListOfAuctionsNotRelatedToAnNonExistingHouse() throws Exception {
        String expectedJsonContent = "[]";
        String AUCTION_TO_HOUSE_BY_ID_URL = "/auction-houses/42/auctions";
        this.mockMvc.perform(get(AUCTION_TO_HOUSE_BY_ID_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void shouldReturnBadRequestStatusWhenAnInvalidIdIsProvided() throws Exception {
        String AUCTION_TO_HOUSE_BY_NULL_URL = "/auction-houses/null/auctions";
        this.mockMvc.perform(get(AUCTION_TO_HOUSE_BY_NULL_URL)).andDo(print()).andExpect(status().isBadRequest());
    }
}