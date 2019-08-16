package com.gumichan01.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumichan01.challenge.controller.dto.AuctionDto;
import com.gumichan01.challenge.service.AuctionService;
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

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void shouldReturnEmptyListOfAuctionsNotRelatedToAnNonExistingHouse() throws Exception {
        String expectedJsonContent = "[]";
        String AUCTION_TO_HOUSE_BY_ID_URL = AUCTION_URL + "/42";
        this.mockMvc.perform(get(AUCTION_TO_HOUSE_BY_ID_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void shouldReturnBadRequestStatusWhenAnInvalidIdIsProvided() throws Exception {
        this.mockMvc.perform(get(AUCTION_URL + "/null")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedStatusWithTheRegisteredAuction() throws Exception {
        AuctionDto dto = new AuctionDto("foo", "lorem", Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(), 42.0, 42.0, 1L);
        String jsonContent = jsonOf(dto);
        this.mockMvc.perform(post(AUCTION_URL + "/").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonContent)).andDo(print()).andExpect(status().isCreated()); // This test is just temporary
    }

    private String jsonOf(@NonNull Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}