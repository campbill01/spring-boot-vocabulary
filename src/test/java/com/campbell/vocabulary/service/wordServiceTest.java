package com.campbell.vocabulary.service;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.repository.wordRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class wordServiceTest {

    private wordService service;

    @Autowired
    private wordRepository repository;

    @BeforeAll
    void setUp() {
       /* this.repository = new wordsRepository() {
            @Override
            public <S extends Words> S save(S s) {
                return null;
            }

            @Override
            public <S extends Words> Iterable<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public Optional<Words> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public Iterable<Words> findAll() {
                return null;
            }

            @Override
            public Iterable<Words> findAllById(Iterable<Long> iterable) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Words words) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> iterable) {

            }

            @Override
            public void deleteAll(Iterable<? extends Words> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public Words findFirstByOrderById() {
                return null;
            }
        };*/
        this.service = new wordService(this.repository);
    }

    @Test
    void create() {
        Word word = new Word("createTest", "works");
        Word resp = this.service.create(word.getName(), word.getMeaning());
        assertEquals("works", resp.getMeaning());
    }

    @Test
    void get() {
        Word word = new Word("createTest", "works");
        //Words resp = this.service.create(word.getWord(), word.getMeaning());
        Word resp = repository.save(word);
        Word retrievedWord = repository.findFirstByOrderByIdAsc();
        //Words retrievedWord = this.service.get(word);
        assertEquals(resp, retrievedWord);
    }
}