package com.gumichan01.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumichan01.challenge.domain.AuctionHouse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChallengeApplicationTest {

    private static final String AUCTION_HOUSES_URL = "/houses";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyListOfAuctionHouses() throws Exception {
        String expectedJsonContent = "[]";
        this.mockMvc.perform(get(AUCTION_HOUSES_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void addAuctionHouseByProvidingItsNameAndReturnTheObjectInJsonFormat() throws Exception {
        String expectedJsonContent = "{\"id\":1,\"name\"=\"mock house\"}";
        String jsonRequestContent = jsonOf(new AuctionHouse("mock house"));

        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void shouldNotregisterTheAuctionHouseTwice() throws Exception {
        String jsonRequestContent = jsonOf(new AuctionHouse("mock house"));
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andExpect(status().isCreated());

        // This must fail
        this.mockMvc.perform(post(AUCTION_HOUSES_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequestContent))
                .andDo(print()).andExpect(status().isConflict());

    }

    @Test
    public void deleteAuctionHouseByIdAndGetNoContent() throws Exception {

        String deleteUrl = AUCTION_HOUSES_URL + "/1";

        this.mockMvc.perform(delete(deleteUrl).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isNoContent());
    }

    private String jsonOf(@NonNull Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
