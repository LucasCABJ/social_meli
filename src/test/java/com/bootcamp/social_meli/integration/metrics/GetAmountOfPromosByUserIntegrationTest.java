package com.bootcamp.social_meli.integration.metrics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAmountOfPromosByUserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnPromosCountAndStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/promo-post/count")
                        .param("user_id", "2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_name").value("Pepito"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amountOfPromos").value(6));
    }

    @Test
    void shouldReturnStatus400WhenUserIdIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/promo-post/count")
                        .param("user_id", "invalid"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnStatus404WhenUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/promo-post/count")
                        .param("user_id", "9999"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}