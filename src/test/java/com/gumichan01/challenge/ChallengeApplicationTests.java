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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyListOfAuctionHouses() throws Exception {
        String url = "/houses";
        String expectedJsonContent = "[]";
        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    @Test
    public void addAuctionHouseByProvidingItsNameAndReturnTheObjectInJsonFormat() throws Exception {
        String url = "/houses";
        String expectedJsonContent = "{\"id\":1,\"name\"=\"mock house\"}";
        String jsonRequestContent = jsonOf(new AuctionHouse("mock house"));
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonRequestContent))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent));
    }

    private String jsonOf(@NonNull Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
