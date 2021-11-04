package com.campbell.vocabulary.integration;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.repository.wordRepository;
import com.campbell.vocabulary.service.wordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(wordController.class)
public class WordControllerIntegrationTest {
    private Logger logger =  LogManager.getLogger(WordControllerIntegrationTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private wordRepository repository;

    @Autowired
    private wordService service;

/*
    @BeforeEach
    void setup(){
        wordsService service = new wordsService(repository);
        wordController controller = new wordController(service);
    }
*/
    @Order(1)
    @Test
    void create() throws Exception {
        mvc.perform(post("/words/add/{name}/{meaning}", "test","me"))
                .andExpect(status().isOk());

        Word word = service.get("test");
        logger.log(Level.ALL, "WordsController integration test: create, word is: " +word);
        assertEquals("me", word.getMeaning());
    }

    @Order(2)
    @Test
    void get() throws Exception {
        mvc.perform(post("/words/add/{name}/{meaning}", "test","me"))
                .andExpect(status().isOk());

        String result = mvc.perform(MockMvcRequestBuilders.get("/get/{word}", "test")).andReturn().getResponse().getContentAsString();
        assertNotNull(result);
        logger.log(Level.ALL, "WordsController integration test: get, result is: " +result);
        assertTrue(result.contains("\"name\":\"test\""));
    }

    @Order(3)
    @Test
    void update() throws Exception {
        mvc.perform(post("/words/add/{name}/{meaning}", "test","me"))
                .andExpect(status().isOk());

        mvc.perform(post("/words/update/{name}/{meaning}", "test","meAgain"))
                .andExpect(status().isOk());

        Word word = service.get("test");
        logger.log(Level.ALL, "WordsController integration test: update, word is: " +word);
        assertEquals("meAgain", word.getMeaning());
    }

    @Order(4)
    @Test
    void delete() throws Exception {
        mvc.perform(post("/words/delete/{name}", "test"))
                .andExpect(status().isOk());

        String result = mvc.perform(MockMvcRequestBuilders.get("/get/{name}", "test")).andReturn().getResponse().getContentAsString();
        assertEquals("", result);
    }
}
