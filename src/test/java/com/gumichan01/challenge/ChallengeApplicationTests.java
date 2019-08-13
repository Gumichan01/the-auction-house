package com.gumichan01.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
