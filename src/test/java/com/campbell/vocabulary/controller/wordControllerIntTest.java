package com.campbell.vocabulary.controller;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.repository.wordRepository;
import com.campbell.vocabulary.service.wordService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@WebMvcTest(wordController.class)
class wordControllerIntTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private wordRepository repository;

    @SpyBean
    private wordService service;

    private static Logger logger = LogManager.getLogger(wordControllerIntTest.class);

    @BeforeEach
    public void setup(){
        wordService service = new wordService(repository);
    }

    @Test
    void get() throws Exception {
        Word word = new Word("thing", "one");
        Mockito.when(repository.findFirstByOrderById()).thenReturn(word);
        String result = mvc.perform(MockMvcRequestBuilders.get("/get")).andReturn().getResponse().getContentAsString();
        logger.log(Level.ALL, "Testing get, the result was " + result);
        assertNotNull(result);
        assertTrue(result.contains("thing"));
    }

    @Test
    void create() throws Exception {
        Word word = new Word("thing", "one");
        Mockito.when(repository.save(word)).thenReturn(word);
        String result = mvc.perform(MockMvcRequestBuilders.post("/words/add/thing/one")).andReturn().getResponse().getContentAsString();
        logger.log(Level.ALL, "Testing create, the result was " + result);
        assertNotNull(result);
        assertTrue(result.contains("thing"));
    }
}