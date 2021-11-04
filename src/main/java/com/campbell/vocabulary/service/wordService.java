package com.campbell.vocabulary.service;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.repository.wordRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class wordService {
    private final wordRepository wordrepository;
    private static Logger logger = LogManager.getLogger(wordService.class);

    @Autowired
    public wordService(wordRepository wordrepository) {
        this.wordrepository = wordrepository;
    }

    public List<Word> list(){
        List<Word> listOfWords = new ArrayList();
        for( Word word: wordrepository.findAll()){
            listOfWords.add(word);
        }
        return listOfWords;
    }

    public Word create(String name, String meaning){
        Word newWord = new Word(name, meaning);
        wordrepository.save(newWord);

        return newWord;
    }

    public Word get(){
        return wordrepository.findFirstByOrderById();
    }

    public Word get(String word){
        Iterable<Word> words = wordrepository.findAll();
        for(Word wordInList: words){
            //logger.log(Level.ALL, "Searching list of words in repository, current word: " + wordInList);
            if(wordInList.getName().equals(word)){
                return wordInList;
            }
        }
        return null;

    }

    public Word random() {
        Iterable <Word> wordsList = wordrepository.findAll();
        List<Word> newList = new ArrayList<>();
        for(Word word: wordsList) {
            newList.add(word);
        }
        int length = newList.size();
        int min = 1;
        int range = length -  1;
        // math.random returns double between 0.0 an 1.0
        // need to adjust for size of list
        int intValue = (int)(Math.random() * range + min);
        Long Id = Long.valueOf(intValue);
        Optional<Word> optionalWord = wordrepository.findById(Id);
        if(optionalWord.isPresent()){
            return optionalWord.get();
        }
        return null;
    }

    public Word update(Word word){
        logger.log(Level.ALL, "Update: looking up word: " + word);
        Optional<Word> optionalUpdateWord = wordrepository.findById(word.getId());
        Word updateWord = new Word();
        if( optionalUpdateWord.isPresent()){
            updateWord = optionalUpdateWord.get();
        }
        updateWord.setName(word.name);
        updateWord.setMeaning(word.meaning);

        wordrepository.save(updateWord);
        return updateWord;
    }

    public String delete(Word word){
        try {
            wordrepository.delete(word);
        }catch (Exception e){
            return "Unable to delete " + word + " " + e;
        }
        return "Deleted " + word;
    }

}
