package com.Sergio.urlShortener.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UrlControllerTest {

    private static final Gson gson = new Gson();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UrlController urlController;

    @Test
    public void shouldNotAcceptNoUrl() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAcceptInvalidUrl() throws Exception {
        mockMvc.perform(post("/").param("link", "aninvalidurl"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAcceptValidUrl() throws Exception {
        mockMvc.perform(post("/")
                .content("http://www.google.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnSameShortUrlForSameUrl() throws Exception{
        MvcResult result = mockMvc.perform(post("/")
                .content("http://www.google.com"))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mockMvc.perform(post("/")
                .content("http://www.google.com"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), result2.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotReturnSameShortUrlForDifferentUrls() throws Exception{
        MvcResult result = mockMvc.perform(post("/")
                .content("http://www.facebook.com"))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mockMvc.perform(post("/")
                .content("http://www.google.com"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals(result.getResponse().getContentAsString(), result2.getResponse().getContentAsString());
    }
}
